package cronos.alice.web.api;

import cronos.alice.model.AuthenticationRequest;
import cronos.alice.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class LoginController {

	private final LoginService loginService;

	@Autowired
	public LoginController(final LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
		return loginService.authenticate(authenticationRequest);
	}
}
