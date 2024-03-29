package cronos.alice.web.api;

import org.springframework.beans.factory.annotation.Autowired;
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

import cronos.alice.model.dto.PasswordDto;
import cronos.alice.model.dto.UserDto;
import cronos.alice.model.entity.User;
import cronos.alice.service.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/user/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		return new ResponseEntity<>(userService.convertToDto(user), HttpStatus.OK);
	}

	@PostMapping(value = "/user", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
		User user = new User();
		userService.updateUserFields(dto, user);
		User userCreated = userService.save(user);
		return new ResponseEntity<>(userService.convertToDto(userCreated), HttpStatus.OK);
	}

	@PutMapping(value = "/user/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> update(@RequestBody UserDto dto, @PathVariable Long id) {
		User user = userService.findById(id);
		userService.updateUserFields(dto, user);
		User userCreated = userService.save(user);
		return new ResponseEntity<>(userService.convertToDto(userCreated), HttpStatus.OK);
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

	@PutMapping(value = "/changePassword", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePassword(@RequestBody PasswordDto passwordDto) {
		return new ResponseEntity<>(userService.changePassword(passwordDto), HttpStatus.OK);
	}
}
