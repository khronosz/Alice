package cronos.alice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cronos.alice.model.dto.DashboardDto;
import cronos.alice.model.dto.NonUtilizedUserDto;
import cronos.alice.model.dto.NonValidatedUserDto;
import cronos.alice.model.dto.ProjectForDashboardDto;
import cronos.alice.model.entity.User;

@Service
public class DashboardService {

	private static final Logger log = LogManager.getLogger(DashboardService.class);

	private final UserService userService;

	private final ProjectService projectService;

	@Autowired
	public DashboardService(final UserService userService, final ProjectService projectService) {
		this.userService = userService;
		this.projectService = projectService;
	}

	public DashboardDto getDashboard(User user) {
		log.info("Get dashboard for user: " + user.getUsername());
		return new DashboardDto(getNonUtilizedUsers(user.getId()), getNonValidatedUsers(user.getId()), findAllProjectDashboardDto(user.getId()));
	}

	private List<NonUtilizedUserDto> getNonUtilizedUsers(Long userId) {
		return userService.getNonUtilizedUsers(userId);
	}

	private List<NonValidatedUserDto> getNonValidatedUsers(Long userId) {
		return userService.getNonValidatedUsers(userId);
	}

	private List<ProjectForDashboardDto> findAllProjectDashboardDto(Long userId) {
		return projectService.findAllProjectDashboardDto(userId);
	}
}
