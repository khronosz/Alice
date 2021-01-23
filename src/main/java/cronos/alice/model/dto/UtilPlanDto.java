package cronos.alice.model.dto;

import java.time.LocalDate;

import com.querydsl.core.Tuple;

public class UtilPlanDto {

	private final Long id;
	private final String username;
	private final String job;
	private final String department;
	private final String level;
	private final Long directManagerId;
	private final String directManagerName;
	private String projectNames;
	private final Integer utilization;
	private final LocalDate projectEnd;
	private final String notes;

	public UtilPlanDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.username = tuple.get(1, String.class);
		this.job = tuple.get(2, String.class);
		this.department = tuple.get(3, String.class);
		this.level = tuple.get(4, String.class);
		this.directManagerId = tuple.get(5, Long.class);
		this.directManagerName = tuple.get(6, String.class);
		this.utilization = tuple.get(7, Integer.class);
		this.projectEnd = tuple.get(8, LocalDate.class);
		this.notes = tuple.get(9, String.class);
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

	public String getLevel() {
		return level;
	}

	public Long getDirectManagerId() {
		return directManagerId;
	}

	public String getDirectManagerName() {
		return directManagerName;
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
}
