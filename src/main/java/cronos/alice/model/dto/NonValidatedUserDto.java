package cronos.alice.model.dto;

import java.time.LocalDate;

import com.querydsl.core.Tuple;

public class NonValidatedUserDto {

	private final Long id;
	private final String username;
	private final String job;
	private final String level;
	private String projectNames;
	private final LocalDate lastValidation;

	public NonValidatedUserDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.username = tuple.get(1, String.class);
		this.job = tuple.get(2, String.class);
		this.level = tuple.get(3, String.class);
		this.lastValidation = tuple.get(4, LocalDate.class);
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

	public String getLevel() {
		return level;
	}

	public String getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(final String projectNames) {
		this.projectNames = projectNames;
	}

	public LocalDate getLastValidation() {
		return lastValidation;
	}
}
