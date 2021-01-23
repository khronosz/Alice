package cronos.alice.web.api;

import cronos.alice.model.dto.UtilPlanDto;
import cronos.alice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class UtilPlanController {

	private final UserService userService;

	@Autowired
	public UtilPlanController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/utilization")
	public ResponseEntity<List<UtilPlanDto>> findAllUtilPlanDto() {
		return new ResponseEntity<>(userService.findAllUtilPlanDto(), HttpStatus.OK);
	}

	@GetMapping(value = "/utilization/export")
	public void export(HttpServletResponse response) {
		userService.exportUtilPlan(response);
	}
}
