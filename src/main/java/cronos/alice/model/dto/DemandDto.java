package cronos.alice.model.dto;

import java.time.LocalDate;

import com.querydsl.core.Tuple;
import cronos.alice.model.entity.Demand;

public class DemandDto {

	private Long id;
	private String name;
	private String mib;
	private Long userId;
	private String username;
	private Long projectId;
	private String projectName;
	private Integer utilization;
	private LocalDate projectStart;
	private LocalDate projectEnd;

	public DemandDto() {
	}

	public DemandDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.name = tuple.get(1, String.class);
		this.mib = tuple.get(2, String.class);
		this.userId = tuple.get(3, Long.class);
		this.username = tuple.get(4, String.class);
		this.projectId = tuple.get(5, Long.class);
		this.projectName = tuple.get(6, String.class);
		this.utilization = tuple.get(7, Integer.class);
		this.projectStart = tuple.get(8, LocalDate.class);
		this.projectEnd = tuple.get(9, LocalDate.class);
	}

	public DemandDto(Demand demand) {
		this.id = demand.getId();
		this.name = demand.getName();
		this.mib = demand.getMib();
		this.userId = demand.getUserId();
		this.projectId = demand.getProjectId();
		this.utilization = demand.getUtilization();
		this.projectStart = demand.getProjectStart();
		this.projectEnd = demand.getProjectEnd();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMib() {
		return mib;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public Integer getUtilization() {
		return utilization;
	}

	public LocalDate getProjectStart() {
		return projectStart;
	}

	public LocalDate getProjectEnd() {
		return projectEnd;
	}
}
