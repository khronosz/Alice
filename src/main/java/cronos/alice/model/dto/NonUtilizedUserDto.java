package cronos.alice.model.dto;

import com.querydsl.core.Tuple;

public class NonUtilizedUserDto {

	private final Long id;
	private final String username;
	private final String job;
	private final String level;
	private String projectNames;
	private final Integer utilization;

	public NonUtilizedUserDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.username = tuple.get(1, String.class);
		this.job = tuple.get(2, String.class);
		this.level = tuple.get(3, String.class);
		this.utilization = tuple.get(4, Integer.class);
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

	public Integer getUtilization() {
		return utilization;
	}
}
