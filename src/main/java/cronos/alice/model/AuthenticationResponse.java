package cronos.alice.model;

import java.time.LocalDate;
import java.util.Set;

public class AuthenticationResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private final Set<String> roles;
	private LocalDate lastLogin;

	public AuthenticationResponse(String accessToken, Long id, String username, Set<String> roles, LocalDate lastLogin) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.lastLogin = lastLogin;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(final LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}
}
