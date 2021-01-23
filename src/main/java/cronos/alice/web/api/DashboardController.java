package cronos.alice.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.DashboardDto;
import cronos.alice.service.DashboardService;
import cronos.alice.service.UserService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class DashboardController {

	private final DashboardService dashboardService;

	private final UserService userService;

	@Autowired
	public DashboardController(final DashboardService dashboardService, UserService userService) {
		this.dashboardService = dashboardService;
		this.userService = userService;
	}

	@GetMapping(value = "/dashboard")
	public ResponseEntity<DashboardDto> getDashboard() {
		return new ResponseEntity<>(dashboardService.getDashboard(userService.getLoggedInUser()), HttpStatus.OK);
	}
}
