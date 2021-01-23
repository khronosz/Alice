package cronos.alice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cronos.alice.model.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Long id;

	private final String username;

	@JsonIgnore
	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	private final LocalDate lastLogin;

	public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities, LocalDate lastLogin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.lastLogin = lastLogin;
	}

	public static UserDetailsImpl build(User user) {
		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toSet());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(),
				user.getPassword(),
				authorities,
				user.getLastLogin());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
