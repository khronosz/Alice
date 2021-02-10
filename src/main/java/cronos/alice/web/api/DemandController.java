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
import cronos.alice.model.entity.Demand;
import cronos.alice.service.DemandService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class DemandController {

	private final DemandService demandService;

	public DemandController(final DemandService demandService) {
		this.demandService = demandService;
	}

	@GetMapping(value = "/project/{id}/demands")
	public ResponseEntity<List<DemandDto>> findAllByProjectId(@PathVariable Long id) {
		return new ResponseEntity<>(demandService.findAllByProjectId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}/demands")
	public ResponseEntity<List<DemandDto>> findAllByUserId(@PathVariable Long id) {
		return new ResponseEntity<>(demandService.findAllByUserId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/project/{projectId}/demand/{id}")
	public ResponseEntity<DemandDto> findById(@PathVariable Long projectId, @PathVariable Long id) {
		Demand demand = demandService.findByProject(projectId, id);
		return new ResponseEntity<>(demandService.convertToDto(demand), HttpStatus.OK);
	}

	@GetMapping(value = "/project/{id}/demands/export")
	public void export(@PathVariable("id") Long id, HttpServletResponse response) {
		demandService.export(id, response);
	}

	@PostMapping(value = "/project/{projectId}/demand", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<DemandDto> save(@RequestBody DemandDto dto, @PathVariable Long projectId) {
		Demand demand = new Demand();
		demandService.updateDemandFields(projectId, dto, demand);
		Demand demandCreated = demandService.save(demand);
		return new ResponseEntity<>(demandService.convertToDto(demandCreated), HttpStatus.OK);
	}

	@PutMapping(value = "/project/{projectId}/demand/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<DemandDto> update(@RequestBody DemandDto dto, @PathVariable Long projectId, @PathVariable Long id) {
		Demand demand = demandService.findById(id);
		demandService.updateDemandFields(projectId, dto, demand);
		Demand demandCreated = demandService.save(demand);
		return new ResponseEntity<>(demandService.convertToDto(demandCreated), HttpStatus.OK);
	}

	@DeleteMapping(value = "/project/{projectId}/demand/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable Long projectId, @PathVariable Long id) {
		try {
			demandService.deleteById(projectId, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
