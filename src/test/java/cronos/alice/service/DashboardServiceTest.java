package cronos.alice.service;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.dto.DashboardDto;
import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.model.entity.Role;
import cronos.alice.model.entity.User;
import cronos.alice.model.enums.user.ERole;
import cronos.alice.repository.RoleRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class DashboardServiceTest {

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DemandService demandService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	private User manager;
	private User user1;
	private User user2;
	private User user3;
	private Project project;

	@Before
	public void initData() {
		initManager();
		initTeam();
		initProject();
		initDemands();
	}

	@Test
	public void testGetDashboard() {
		DashboardDto dto = dashboardService.getDashboard(manager);
		assertEquals(2, dto.getNonUtilizedUsers().size());
		assertEquals(3, dto.getNonValidatedUsers().size());
		assertEquals(1, dto.getProjects().size());
	}

	private void initManager() {
		manager = new User("Direct Manager", "tester", "IT", "boss@test.com", "Los Angeles", "Architect");
		manager.getRoles().add(roleRepository.save(new Role(ERole.ROLE_USER)));
		userService.save(manager);
	}

	private void initTeam() {
		user1 = new User("Jane Doe", "tester", "IT", "jane@test.com", "Los Angeles", "Architect");
		user2 = new User("Jon Doe", "tester", "IT", "jon@test.com", "Los Angeles", "Junior");
		user3 = new User("Jack Doe", "tester", "IT", "jack@test.com", "Los Angeles", "Experienced");
		user1.setDirectManagerId(manager.getId());
		user2.setDirectManagerId(manager.getId());
		user3.setDirectManagerId(manager.getId());
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);
	}

	private void initProject() {
		project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		projectService.save(project);
	}

	private void initDemands() {
		Demand demand1 = new Demand("Demand 1", "Mib 1", project.getId(), user1.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1));
		Demand demand2 = new Demand("Demand 2", "Mib 2", project.getId(), user2.getId(), 100, LocalDate.now(), LocalDate.now().plusMonths(1));
		Demand demand3 = new Demand("Demand 3", "Mib 3", project.getId(), user3.getId(), 80, LocalDate.now(), LocalDate.now().plusMonths(1));
		demandService.save(demand1);
		demandService.save(demand2);
		demandService.save(demand3);
	}
}
