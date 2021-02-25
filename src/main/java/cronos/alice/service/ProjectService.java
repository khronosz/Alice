package cronos.alice.service;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cronos.alice.exception.ExportFailedException;
import cronos.alice.exception.IllegalDateException;
import cronos.alice.exception.ResourceNotFoundException;
import cronos.alice.exception.UniqueProjectsSapException;
import cronos.alice.model.dto.DemandDto;
import cronos.alice.model.dto.ProjectDescriptionDto;
import cronos.alice.model.dto.ProjectDto;
import cronos.alice.model.dto.ProjectForDashboardDto;
import cronos.alice.model.entity.Project;
import cronos.alice.repository.ProjectRepository;
import cronos.alice.util.ProjectExporter;

@Service
public class ProjectService {

	private static final Logger log = LogManager.getLogger(ProjectService.class);

	private final ProjectRepository projectRepository;

	private final DemandService demandService;

	private List<Project> projects;

	@Autowired
	public ProjectService(final ProjectRepository projectRepository, final DemandService demandService) {
		this.projectRepository = projectRepository;
		this.demandService = demandService;
	}

	public Project findById(Long id) {
		log.info("Find project by ID: " + id);
		return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with the ID: " + id));
	}

	public List<ProjectDto> findAll() {
		log.info("Find all projects");
		return projectRepository.findAll().stream().sorted(Comparator.comparing(Project::getProjectName)).map(ProjectDto::new).collect(Collectors.toList());
	}

	public List<ProjectDto> findAllDtoBySearchText(String searchText) {
		log.info("Find all projects by search text: " + searchText);
		return projectRepository.findAllDtoBySearchText(searchText).stream().map(ProjectDto::new).collect(Collectors.toList());
	}

	public List<Long> findAllIds() {
		log.info("Find all project IDs");
		return projectRepository.findAllIds();
	}

	public void export(HttpServletResponse response) {
		try {
			ByteArrayInputStream stream = new ProjectExporter(createMap()).export(response);
			IOUtils.copy(stream, response.getOutputStream());
			log.info("Export all projects to excel file");
		} catch (Exception e) {
			log.error("Project export failed " + e.getMessage());
			throw new ExportFailedException("Project export failed!");
		}
	}

	private Map<ProjectDto, List<DemandDto>> createMap() {
		return findAll().stream().sorted(Comparator.comparing(ProjectDto::getProjectName)).collect(Collectors.toMap(project -> project, this::apply, (a, b) -> b));
	}

	private List<DemandDto> apply(ProjectDto projectDto) {
		return demandService.findAllForExcel().stream().filter(demand -> demand.getProjectId().equals(projectDto.getId())).collect(Collectors.toList());
	}

	public List<ProjectDescriptionDto> findAllProjectDescriptionDto() {
		return projectRepository.findAll().stream().sorted(Comparator.comparing(Project::getProjectName)).map(ProjectDescriptionDto::new).collect(Collectors.toList());
	}

	public List<ProjectForDashboardDto> findAllProjectDashboardDto(Long userId) {
		return projectRepository.findAllProjectDashboardDto(userId).stream().map(ProjectForDashboardDto::new).collect(Collectors.toList());
	}

	@Transactional
	public Project save(Project project) {
		projects = projectRepository.findAll();
		projects.forEach(p -> {
			if (p.getSap().equalsIgnoreCase(project.getSap()) && !p.getId().equals(project.getId())) throw new UniqueProjectsSapException("SAP number already exists!");
		});
		if (project.getStart() != null && project.getEnd() != null && (project.getEnd().isBefore(project.getStart()) || project.getEnd().isEqual(project.getStart()))) {
			throw new IllegalDateException("End date cannot be earlier than start date!");
		}
		log.info("Save project: " + project.getProjectName());
		return projectRepository.save(project);
	}

	public void updateProjectFields(final ProjectDto dto, final Project project) {
		log.debug("Convert ProjectDto to entity: " + dto.getProjectName());
		project.setProjectName(dto.getProjectName());
		project.setSap(dto.getSap());
		project.setPhase(dto.getPhase());
		project.setStatus(dto.getStatus());
		project.setManager(dto.getManager());
		project.setBackupManager(dto.getBackupManager());
		project.setOwner(dto.getOwner());
		project.setCustomer(dto.getCustomer());
		project.setBu(dto.getBu());
		project.setBuHu(dto.getBuHu());
		project.setContactPerson(dto.getContactPerson());
		project.setFte(dto.getFte());
		project.setStart(dto.getStart());
		project.setEnd(dto.getEnd());
		project.setOrderType(dto.getOrderType());
		project.setProjectType(dto.getProjectType());
		project.setDescription(dto.getDescription());
		project.setComment(dto.getComment());
	}

	public ProjectDto convertToDto(Project project) {
		log.debug("Convert Project entity to dto: " + project.getProjectName());
		return new ProjectDto(project);
	}

	@Transactional
	public void deleteById(Long projectId) {
		if (!isValidProjectId(projectId)) throw new ResourceNotFoundException("Project not found with the ID: " + projectId);
		log.warn("Delete project by ID: " + projectId);
		projectRepository.deleteById(projectId);
	}

	private Boolean isValidProjectId(Long projectId) {
		projects = projectRepository.findAll();
		int hint = (int) projects.stream().filter(p -> p.getId().equals(projectId)).count();
		return hint != 0;
	}
}
