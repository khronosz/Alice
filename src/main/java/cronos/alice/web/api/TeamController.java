package cronos.alice.web.api;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.TeamDto;
import cronos.alice.service.UserService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class TeamController {

	private final UserService userService;

	@Autowired
	public TeamController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/team")
	public ResponseEntity<List<TeamDto>> findAllTeamDto() {
		return new ResponseEntity<>(userService.findAllTeamDto(userService.getLoggedInUser().getId()), HttpStatus.OK);
	}

	@GetMapping(value = "/directManagers")
	public ResponseEntity<List<String>> findAllDirectManagers() {
		return new ResponseEntity<>(userService.findAllDirectManagers(), HttpStatus.OK);
	}

	@GetMapping(value = "/usernames")
	public ResponseEntity<List<String>> findAllUsernames() {
		return new ResponseEntity<>(userService.findAllUsernames(), HttpStatus.OK);
	}

	@GetMapping(value = "/team/export")
	public void export(HttpServletResponse response) {
		userService.exportTeam(response);
	}

	@PutMapping(value = "/team/{id}")
	public ResponseEntity<TeamDto> updateLastValidation(@PathVariable Long id) {
		return new ResponseEntity<>(userService.updateLastValidation(id), HttpStatus.OK);
	}

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
		try {
			userService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
