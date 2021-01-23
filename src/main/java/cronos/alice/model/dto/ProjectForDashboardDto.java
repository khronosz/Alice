package cronos.alice.model.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.querydsl.core.Tuple;

public class ProjectForDashboardDto {

	private final Long id;
	private final String projectName;
	private final String phase;
	private final String status;
	private final String manager;
	private final String customer;
	private final Integer fte;
	private final LocalDate end;

	public ProjectForDashboardDto(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.projectName = tuple.get(1, String.class);
		this.phase = tuple.get(2, String.class);
		this.status = tuple.get(3, String.class);
		this.manager = tuple.get(4, String.class);
		this.customer = tuple.get(5, String.class);
		this.fte = tuple.get(6, Integer.class);
		this.end = tuple.get(7, LocalDate.class);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ProjectForDashboardDto that = (ProjectForDashboardDto) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(projectName, that.projectName) &&
				Objects.equals(phase, that.phase) &&
				Objects.equals(status, that.status) &&
				Objects.equals(manager, that.manager) &&
				Objects.equals(customer, that.customer) &&
				Objects.equals(fte, that.fte) &&
				Objects.equals(end, that.end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, projectName, phase, status, manager, customer, fte, end);
	}

	public Long getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getPhase() {
		return phase;
	}

	public String getStatus() {
		return status;
	}

	public String getManager() {
		return manager;
	}

	public String getCustomer() {
		return customer;
	}

	public Integer getFte() {
		return fte;
	}

	public LocalDate getEnd() {
		return end;
	}
}
