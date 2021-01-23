package cronos.alice.service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import cronos.alice.model.AuthenticationRequest;
import cronos.alice.model.AuthenticationResponse;
import cronos.alice.model.UserDetailsImpl;
import cronos.alice.model.entity.User;
import cronos.alice.util.JwtProvider;

@Service
public class LoginService {

	private static final Logger log = LogManager.getLogger(LoginService.class);

	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	private final UserService userService;

	@Autowired
	public LoginService(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider, final UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.userService = userService;
	}

	@Transactional
	public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
		log.info("Login attempt with the username: " + authenticationRequest.getUsername());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Set<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		LocalDate lastLogin = getLastLogin(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				roles,
				lastLogin));
	}

	private LocalDate getLastLogin(final UserDetailsImpl userDetails) {
		LocalDate lastLogin = userDetails.getLastLogin();
		User user = userService.getLoggedInUser();
		user.setLastLogin(LocalDate.now());
		userService.save(user);
		return lastLogin;
	}
}
