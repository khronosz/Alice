package cronos.alice.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.TeamDto;
import cronos.alice.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping
public class AdminController {

	private final UserService userService;

	@Autowired
	public AdminController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping(value = "/admin/users")
	public ResponseEntity<List<TeamDto>> findAllTeamDto() {
		return new ResponseEntity<>(userService.findAllTeamDtoForAdmin(), HttpStatus.OK);
	}


}
