package cronos.alice.model.dto;

import java.util.List;

public class DashboardDto {

	private final List<NonUtilizedUserDto> nonUtilizedUsers;
	private final List<NonValidatedUserDto> nonValidatedUsers;
	private final List<ProjectForDashboardDto> projects;

	public DashboardDto(final List<NonUtilizedUserDto> nonUtilizedUsers, final List<NonValidatedUserDto> nonValidatedUsers, final List<ProjectForDashboardDto> projects) {
		this.nonUtilizedUsers = nonUtilizedUsers;
		this.nonValidatedUsers = nonValidatedUsers;
		this.projects = projects;
	}

	public List<NonUtilizedUserDto> getNonUtilizedUsers() {
		return nonUtilizedUsers;
	}

	public List<NonValidatedUserDto> getNonValidatedUsers() {
		return nonValidatedUsers;
	}

	public List<ProjectForDashboardDto> getProjects() {
		return projects;
	}
}
