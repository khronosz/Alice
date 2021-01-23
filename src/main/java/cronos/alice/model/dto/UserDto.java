package cronos.alice.model.dto;

import java.time.LocalDate;

import cronos.alice.model.entity.User;

public class UserDto {

	private Long id;
	private String username;
	private String job;
	private String department;
	private String email;
	private String city;
	private String level;
	private Long directManagerId;
	private String directManagerName;
	private String notes;
	private LocalDate lastValidation;
	private LocalDate lastLogin;

	public UserDto() {
	}

	public UserDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.job = user.getJob();
		this.department = user.getDepartment();
		this.email = user.getEmail();
		this.city = user.getCity();
		this.level = user.getLevel();
		this.directManagerId = user.getDirectManagerId();
		this.notes = user.getNotes();
		this.lastValidation = user.getLastValidation();
		this.lastLogin = user.getLastLogin();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getJob() {
		return job;
	}

	public String getDepartment() {
		return department;
	}

	public String getEmail() {
		return email;
	}

	public String getCity() {
		return city;
	}

	public String getLevel() {
		return level;
	}

	public Long getDirectManagerId() {
		return directManagerId;
	}

	public String getDirectManagerName() {
		return directManagerName;
	}

	public void setDirectManagerName(final String directManagerName) {
		this.directManagerName = directManagerName;
	}

	public String getNotes() {
		return notes;
	}

	public LocalDate getLastValidation() {
		return lastValidation;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}
}
