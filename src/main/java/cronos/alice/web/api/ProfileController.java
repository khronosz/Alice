package cronos.alice.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.UserDto;
import cronos.alice.model.entity.User;
import cronos.alice.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class ProfileController {

	private final UserService userService;

	@Autowired
	public ProfileController(final UserService userService) {
		this.userService = userService;
	}

	@PutMapping(value = "/profile", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> update(@RequestBody UserDto dto) {
		User user = userService.convertToEntity(dto);
		User userCreated = userService.save(user);
		return new ResponseEntity<>(userService.convertToDto(userCreated), HttpStatus.OK);
	}
}
