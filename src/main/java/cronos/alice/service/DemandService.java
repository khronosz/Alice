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
import cronos.alice.exception.UtilizationTooMuchException;
import cronos.alice.model.dto.DemandDto;
import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.repository.DemandRepository;
import cronos.alice.repository.ProjectRepository;
import cronos.alice.util.DemandExporter;

@Service
public class DemandService {

	private static final Logger log = LogManager.getLogger(DemandService.class);

	private final DemandRepository demandRepository;

	private final ProjectRepository projectRepository;

	private final UserService userService;

	@Autowired
	public DemandService(final DemandRepository demandRepository, final ProjectRepository projectRepository, final UserService userService) {
		this.demandRepository = demandRepository;
		this.projectRepository = projectRepository;
		this.userService = userService;
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
			Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found with the ID: " + id));
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
		if (demand.getProjectStart() != null && demand.getProjectEnd() != null && demand.getProjectEnd().isBefore(demand.getProjectStart())) {
			throw new IllegalDateException("End date cannot be earlier than start date!");
		}
		if (demand.getUserId() != null) {
			Integer totalUtilization = demandRepository.getTotalUtilizationByUser(demand.getUserId(), demand.getId());
			if (totalUtilization == null) totalUtilization = demand.getUtilization();
			if (totalUtilization > 100) {
				throw new UtilizationTooMuchException("Utilization cannot be more than 100!");
			}
		}
		log.info("Save demand: " + demand.getName());
		return demandRepository.save(demand);
	}

	public Demand convertToEntity(DemandDto dto) {
		Demand demand = dto.getId() != null ? findById(dto.getId()) : new Demand();
		updateDemandFields(dto, demand);
		return demand;
	}

	private void updateDemandFields(final DemandDto dto, final Demand demand) {
		demand.setName(dto.getName());
		demand.setMib(dto.getMib());
		demand.setProjectId(dto.getProjectId());
		if (dto.getUserId() == null) {
			demand.setUserId(null);
			demand.setProjectStart(null);
			demand.setProjectEnd(null);
			demand.setUtilization(0);
		} else {
			demand.setUserId(dto.getUserId());
			demand.setProjectStart(dto.getProjectStart());
			demand.setProjectEnd(dto.getProjectEnd());
			demand.setUtilization(dto.getUtilization());
		}
	}

	public DemandDto convertToDto(Demand demand) {
		Project project = projectRepository.findById(demand.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found with the ID: " + demand.getProjectId()));
		DemandDto dto = new DemandDto(demand);
		dto.setUsername(userService.findById(demand.getUserId()).getUsername());
		dto.setProjectName(project.getProjectName());
		return dto;
	}

	public Demand findById(Long id) {
		log.info("Find demand by ID: " + id);
		return demandRepository.findById(id).orElseThrow(() -> new RuntimeException("Demand not found with the ID: " + id));
	}

	@Transactional
	public void deleteById(Long id) {
		log.warn("Delete demand by ID: " + id);
		demandRepository.deleteById(id);
	}
}
