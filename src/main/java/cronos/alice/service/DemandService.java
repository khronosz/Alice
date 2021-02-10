package cronos.alice.service;

import java.io.ByteArrayInputStream;
import java.util.List;
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
import cronos.alice.exception.UniqueDemandNameException;
import cronos.alice.exception.UniqueDemandsMibException;
import cronos.alice.exception.UniqueDemandsUserProjectException;
import cronos.alice.exception.UtilizationTooMuchException;
import cronos.alice.model.dto.DemandDto;
import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.model.entity.User;
import cronos.alice.repository.DemandRepository;
import cronos.alice.repository.ProjectRepository;
import cronos.alice.util.DemandExporter;

@Service
public class DemandService {

	private static final Logger log = LogManager.getLogger(DemandService.class);

	private final DemandRepository demandRepository;

	private final ProjectRepository projectRepository;

	private final UserService userService;

	private List<Demand> demands;

	private final List<Project> projects;

	@Autowired
	public DemandService(final DemandRepository demandRepository, final ProjectRepository projectRepository, final UserService userService) {
		this.demandRepository = demandRepository;
		this.projectRepository = projectRepository;
		this.userService = userService;
		this.projects = projectRepository.findAll();
	}

	public List<DemandDto> findAllByUserId(Long id) {
		log.info("Find all demands by user ID: " + id);
		return demandRepository.findAllDtoByUserId(id).stream().map(DemandDto::new).collect(Collectors.toList());
	}

	public List<DemandDto> findAllByProjectId(Long projectId) {
		log.info("Find all demands by project ID: " + projectId);
		return demandRepository.findAllDtoByProjectId(projectId).stream().map(DemandDto::new).collect(Collectors.toList());
	}

	public List<DemandDto> findAllForExcel() {
		return demandRepository.findAllDto().stream().map(DemandDto::new).collect(Collectors.toList());
	}

	public void export(Long id, HttpServletResponse response) {
		try {
			Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with the ID: " + id));
			ByteArrayInputStream stream = new DemandExporter(findAllByProjectId(id), project.getProjectName()).export(response);
			IOUtils.copy(stream, response.getOutputStream());
			log.info("Export all demands to excel file by project ID: " + id);
		} catch (Exception e) {
			log.error("Demand export failed" + e.getMessage());
			throw new ExportFailedException("Demand export failed!");
		}
	}

	@Transactional
	public Demand save(Demand demand) {
		demands = demandRepository.findAll();
		demands.forEach(d -> {
			if (d.getName().equalsIgnoreCase(demand.getName()) && !d.getId().equals(demand.getId())) throw new UniqueDemandNameException("Demand name already exists!");
			if (d.getMib().equalsIgnoreCase(demand.getMib()) && !d.getId().equals(demand.getId())) throw new UniqueDemandsMibException("Demands MIB already exists!");
			if (demand.getUserId() != null && d.getUserId() != null && d.getUserId().equals(demand.getUserId()) && d.getProjectId().equals(demand.getProjectId())	&& !d.getId().equals(demand.getId())) throw new UniqueDemandsUserProjectException("User already exists on the project!");
		});
		if (demand.getProjectStart() != null && demand.getProjectEnd() != null && demand.getProjectEnd().isBefore(demand.getProjectStart())) {
			throw new IllegalDateException("End date cannot be earlier than start date!");
		}
		if (demand.getUserId() != null && demand.getId() != null) {
			Integer currentUtilization = demandRepository.getTotalUtilizationByUser(demand.getUserId(), demand.getId());
			currentUtilization += demand.getUtilization();
			if (currentUtilization > 100) {
				throw new UtilizationTooMuchException("Total utilization of the employee cannot be more than 100!");
			}
		}
		if (demand.getUserId() != null && demand.getId() == null) {
			Integer currentUtilization = demandRepository.getTotalUtilizationByUser(demand.getUserId());
			currentUtilization += demand.getUtilization();
			if (currentUtilization > 100) {
				throw new UtilizationTooMuchException("Total utilization of the employee cannot be more than 100!");
			}
		}
		log.info("Save demand: " + demand.getName());
		return demandRepository.save(demand);
	}

	public void updateDemandFields(final Long projectId, final DemandDto dto, final Demand demand) {
		if (!isValidProjectId(projectId)) throw new ResourceNotFoundException("Project not found with the ID: " + projectId);
		demand.setName(dto.getName());
		demand.setMib(dto.getMib());
		demand.setProjectId(projectId);
		if (dto.getUsername() != null) {
			User user = userService.findByUsername(dto.getUsername());
			demand.setUserId(user.getId());
			demand.setProjectStart(dto.getProjectStart());
			demand.setProjectEnd(dto.getProjectEnd());
			demand.setUtilization(dto.getUtilization() != null ? dto.getUtilization() : 0);
		} else {
			demand.setUserId(null);
			demand.setProjectStart(null);
			demand.setProjectEnd(null);
			demand.setUtilization(0);
		}
	}

	public DemandDto convertToDto(Demand demand) {
		Project project = projectRepository.findById(demand.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found with the ID: " + demand.getProjectId()));
		DemandDto dto = new DemandDto(demand);
		dto.setUsername(demand.getUserId() != null ? userService.findById(demand.getUserId()).getUsername() : "");
		dto.setProjectName(project.getProjectName());
		return dto;
	}

	public Demand findById(Long id) {
		log.info("Find demand by ID: " + id);
		return demandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Demand not found with the ID: " + id));
	}

	public Demand findByProject(Long projectId, Long id) {
		log.info("Find demand by project ID: " + projectId + " and demand ID: " + id);
		Demand demand = findById(id);
		if (!demand.getProjectId().equals(projectId)) throw new ResourceNotFoundException("Resource not found!");
		return demand;
	}

	@Transactional
	public void deleteById(Long projectId, Long id) {
		demands = demandRepository.findAll();
		int hint = (int) demands.stream().filter(d -> d.getProjectId().equals(projectId) && d.getId().equals(id)).count();
		if (hint == 0) throw new ResourceNotFoundException("Demand not found with the ID: " + id);
		log.warn("Delete demand by ID: " + id);
		demandRepository.deleteById(id);
	}

	private Boolean isValidProjectId(Long projectId) {
		int hint = (int) projects.stream().filter(p -> p.getId().equals(projectId)).count();
		return hint != 0;
	}
}
