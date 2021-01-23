package cronos.alice.web.api;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.DemandDto;
import cronos.alice.model.dto.ProjectDto;
import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.service.DemandService;
import cronos.alice.service.ProjectService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class DemandController {

	private final DemandService demandService;

	private final ProjectService projectService;

	public DemandController(final DemandService demandService, final ProjectService projectService) {
		this.demandService = demandService;
		this.projectService = projectService;
	}

	@GetMapping(value = "/project/{id}/demands")
	public ResponseEntity<List<DemandDto>> getDemandsByProjectId(@PathVariable("id") Long id) {
		return new ResponseEntity<>(demandService.findAllByProjectId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}/demands")
	public ResponseEntity<List<DemandDto>> findAllByUserId(@PathVariable("id") Long id) {
		return new ResponseEntity<>(demandService.findAllByUserId(id), HttpStatus.OK);
	}

	@DeleteMapping(value = "/project/{projectId}/demands/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id, @PathVariable String projectId) {
		try {
			demandService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping(value = "/project/{projectId}/demand/{id}")
	public ResponseEntity<DemandDto> findById(@PathVariable Long projectId, @PathVariable("id") Long id) {
		Project project = projectService.findById(projectId);
		if (project != null) {
			Demand demand = demandService.findById(id);
			if (demand != null) return new ResponseEntity<>(demandService.convertToDto(demand), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/project/{projectId}/demand", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<DemandDto> save(@RequestBody DemandDto dto, @PathVariable Long projectId) {
		Demand demand = demandService.convertToEntity(dto);
		Demand demandCreated = demandService.save(demand);
		return new ResponseEntity<>(demandService.convertToDto(demandCreated), HttpStatus.OK);
	}

	@PutMapping(value = "/project/{projectId}/demand", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<DemandDto> update(@RequestBody DemandDto dto, @PathVariable Long projectId) {
		Demand demand = demandService.convertToEntity(dto);
		Demand demandCreated = demandService.save(demand);
		return new ResponseEntity<>(demandService.convertToDto(demandCreated), HttpStatus.OK);
	}

	@PutMapping(value = "/user/{id}/demands", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<DemandDto> updateDemand(@RequestBody DemandDto dto, @PathVariable Long id) {
		Demand demand = demandService.convertToEntity(dto);
		Demand demandCreated = demandService.save(demand);
		return new ResponseEntity<>(demandService.convertToDto(demandCreated), HttpStatus.OK);
	}

	@GetMapping(value = "/project/{id}/demands/export")
	public void export(@PathVariable("id") Long id, HttpServletResponse response) {
		demandService.export(id, response);
	}
}
