package cronos.alice.model.dto;

import java.time.LocalDate;

import com.querydsl.core.Tuple;

public class TeamDto {

	private final Long id;
	private final String username;
	private final String job;
	private final String department;
	private final String email;
	private final String city;
	private final String level;
	private String projectNames;
	private final Integer utilization;
	private final LocalDate projectEnd;
	private final String notes;
	private final LocalDate lastValidation;


	public TeamDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.username = tuple.get(1, String.class);
		this.job = tuple.get(2, String.class);
		this.department = tuple.get(3, String.class);
		this.email = tuple.get(4, String.class);
		this.city = tuple.get(5, String.class);
		this.level = tuple.get(6, String.class);
		this.utilization = tuple.get(7, Integer.class);
		this.projectEnd = tuple.get(8, LocalDate.class);
		this.notes = tuple.get(9, String.class);
		this.lastValidation = tuple.get(10, LocalDate.class);
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

	public String getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(final String projectNames) {
		this.projectNames = projectNames;
	}

	public Integer getUtilization() {
		return utilization;
	}

	public LocalDate getProjectEnd() {
		return projectEnd;
	}

	public String getNotes() {
		return notes;
	}

	public LocalDate getLastValidation() {
		return lastValidation;
	}
}
