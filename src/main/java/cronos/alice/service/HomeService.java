package cronos.alice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cronos.alice.model.dto.ProjectDescriptionDto;
import cronos.alice.model.entity.Project;
import cronos.alice.repository.ProjectRepository;

@Service
public class HomeService {

	private static final Logger log = LogManager.getLogger(HomeService.class);

	private final ProjectService projectService;

	private final ProjectRepository projectRepository;

	@Autowired
	public HomeService(final ProjectService projectService, final ProjectRepository projectRepository) {
		this.projectService = projectService;
		this.projectRepository = projectRepository;
	}

	public List<ProjectDescriptionDto> findAllProjectDescriptionDto() {
		log.info("Get home page");
		return projectService.findAllProjectDescriptionDto();
	}

	@Transactional
	public Project save(Project project) {
		log.info("Save project: " + project.getProjectName());
		return projectRepository.save(project);
	}

	public Project convertToEntity(ProjectDescriptionDto dto) {
		Project project = projectService.findById(dto.getId());
		updateProjectFields(dto, project);
		return project;
	}

	private void updateProjectFields(final ProjectDescriptionDto dto, final Project project) {
		project.setCustomer(dto.getCustomer());
		project.setLongDescription(dto.getDescription());
		project.setTechnology(dto.getTechnology());
	}

	public ProjectDescriptionDto convertToDto(Project project) {
		return new ProjectDescriptionDto(project);
	}
}
