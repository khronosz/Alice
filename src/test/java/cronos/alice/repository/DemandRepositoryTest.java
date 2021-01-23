package cronos.alice.repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.model.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class DemandRepositoryTest {

	@Autowired
	private DemandRepository demandRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	private Project project;
	private Demand demand;
	private User user;

	@Before
	public void initData() {
		project = new Project("Test project", "SAP Number", "phase", "status", "order", "project");
		projectRepository.save(project);
		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		assert projectFromDb != null;
		demand = new Demand("Demand1", "mib1", projectFromDb.getId());
		user = new User("Test User", "tester", "HR", "email@email.com", "city", "Junior");
	}

	@Test
	public void testSaveAndFind() {
		projectRepository.save(project);
		demandRepository.save(demand);
		Demand demandFromDb = demandRepository.findByName("Demand1").orElse(null);
		assert demandFromDb != null;

		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		assert projectFromDb != null;

		assertEquals("Demand1", demandFromDb.getName());
		assertEquals("mib1", demandFromDb.getMib());
		assertEquals(projectFromDb.getId(), demandFromDb.getProjectId());
	}

	@Test
	public void testAssignUser() {
		userRepository.save(user);
		projectRepository.save(project);
		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		User userFromDb = userRepository.findByUsername("Test User").orElse(null);
		assert projectFromDb != null;
		assert userFromDb != null;

		demand.setUserId(userFromDb.getId());
		demand.setUtilization(100);
		demand.setProjectStart(LocalDate.now());
		demand.setProjectEnd(LocalDate.now().plusMonths(1));
		demandRepository.save(demand);

		Demand demandFromDb = demandRepository.findByName("Demand1").orElse(null);
		assert demandFromDb != null;

		assertEquals(projectFromDb.getId(), demandFromDb.getProjectId());
		assertEquals(userFromDb.getId(), demandFromDb.getUserId());
		assertEquals(100, demandFromDb.getUtilization());
		assertEquals(LocalDate.now(), demandFromDb.getProjectStart());
		assertEquals(LocalDate.now().plusMonths(1), demandFromDb.getProjectEnd());
	}

	@Test
	public void testAssignMultipleDemands() {
		userRepository.save(user);
		User userFromDb = userRepository.findByUsername("Test User").orElse(null);
		assert userFromDb != null;

		projectRepository.save(new Project("Test project 1", "SAP Number 1", "phase", "status", "order", "project"));
		projectRepository.save(new Project("Test project 2", "SAP Number 2", "phase", "status", "order", "project"));
		Project projectFromDb1 = projectRepository.findByProjectName("Test project 1").orElse(null);
		Project projectFromDb2 = projectRepository.findByProjectName("Test project 2").orElse(null);
		assert projectFromDb1 != null;
		assert projectFromDb2 != null;

		demandRepository.save(new Demand("Demand1", "mib1", projectFromDb1.getId(), userFromDb.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));
		demandRepository.save(new Demand("Demand2", "mib2", projectFromDb2.getId(), userFromDb.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));
		Demand demandFromDb1 = demandRepository.findByName("Demand1").orElse(null);
		Demand demandFromDb2 = demandRepository.findByName("Demand2").orElse(null);
		assert demandFromDb1 != null;
		assert demandFromDb2 != null;

		assertEquals(userFromDb.getId(), demandFromDb1.getUserId());
		assertEquals(userFromDb.getId(), demandFromDb2.getUserId());
		assertEquals(projectFromDb1.getId(), demandFromDb1.getProjectId());
		assertEquals(projectFromDb2.getId(), demandFromDb2.getProjectId());
	}

	@Test
	public void testUpdate() {
		demandRepository.save(demand);
		Demand demandFromDb = demandRepository.findByName("Demand1").orElse(null);
		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		assert demandFromDb != null;
		assert projectFromDb != null;

		assertEquals("Demand1", demandFromDb.getName());
		assertEquals(projectFromDb.getId(), demandFromDb.getProjectId());

		Demand updatedDemand = updateDemandFields(demandFromDb);
		User userFromDb = userRepository.findByUsername("Test User").orElse(null);
		assert updatedDemand != null;
		assert userFromDb != null;

		Project projectFromDb2 = projectRepository.findByProjectName("Test project 2").orElse(null);
		assert projectFromDb2 != null;

		assertEquals("Demand2", updatedDemand.getName());
		assertEquals(demandFromDb.getId(), updatedDemand.getId());
		assertEquals(100, updatedDemand.getUtilization());
		assertEquals(userFromDb.getId(), updatedDemand.getUserId());
		assertEquals(LocalDate.now(), updatedDemand.getProjectStart());
		assertEquals(LocalDate.now().plusMonths(1), updatedDemand.getProjectEnd());
		assertEquals(projectFromDb2.getId(), updatedDemand.getProjectId());
	}

	//Expected: Project will not be deleted
	@Test
	public void testDelete() {
		projectRepository.save(project);
		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		assert projectFromDb != null;

		demandRepository.save(new Demand("Demand1", "mib1", projectFromDb.getId()));
		demandRepository.save(new Demand("Demand2", "mib2", projectFromDb.getId()));
		List<Demand> demands = demandRepository.findAll();

		assertEquals(2, demands.size());

		Demand demandFromDb = demandRepository.findByName("Demand2").orElse(null);
		assert demandFromDb != null;

		demandRepository.delete(demandFromDb);
		demands = demandRepository.findAll();

		assertEquals(1, demands.size());

		assertEquals(projectFromDb.getId(), demands.get(0).getProjectId());
		assertEquals("Test project", projectFromDb.getProjectName());
		assertEquals("Demand1", demands.get(0).getName());
	}

	//Expected: All demands of the selected project will be deleted as well
	@Test
	public void testDeleteProject() {
		projectRepository.deleteAll();
		projectRepository.save(new Project("Test project 1", "SAP Number 1", "phase", "status", "order", "project"));
		projectRepository.save(new Project("Test project 2", "SAP Number 2", "phase", "status", "order", "project"));
		Project projectFromDb1 = projectRepository.findByProjectName("Test project 1").orElse(null);
		Project projectFromDb2 = projectRepository.findByProjectName("Test project 2").orElse(null);
		assert projectFromDb1 != null;
		assert projectFromDb2 != null;

		demandRepository.save(new Demand("Demand1", "mib1", projectFromDb1.getId()));
		demandRepository.save(new Demand("Demand2", "mib2", projectFromDb1.getId()));
		demandRepository.save(new Demand("Demand3", "mib3", projectFromDb2.getId()));

		List<Project> projects = projectRepository.findAll();
		List<Demand> demands = demandRepository.findAll();

		assertEquals(2, projects.size());
		assertEquals(3, demands.size());

		projectRepository.delete(projectFromDb1);

		projects = projectRepository.findAll();
		demands = demandRepository.findAll();

		assertEquals(1, projects.size());
		assertEquals(1, demands.size());

		assertEquals("Test project 2", projectFromDb2.getProjectName());
		assertEquals("Demand3", demands.get(0).getName());
	}

	//Expected: Demand user_id will be null
	@Test
	public void testDeleteUser() {
		userRepository.save(new User("Test User1", "tester", "HR", "tester1@eamil.com", "city", "Junior"));
		userRepository.save(new User("Test User2", "tester", "HR", "tester2@eamil.com", "city", "Junior"));
		projectRepository.save(project);
		User userFromDb1 = userRepository.findByUsername("Test User1").orElse(null);
		User userFromDb2 = userRepository.findByUsername("Test User2").orElse(null);
		Project projectFromDb = projectRepository.findByProjectName("Test project").orElse(null);
		assert userFromDb1 != null;
		assert userFromDb2 != null;
		assert projectFromDb != null;

		demandRepository.save(new Demand("Demand1", "mib1", projectFromDb.getId(), userFromDb1.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));
		demandRepository.save(new Demand("Demand2", "mib2", projectFromDb.getId(), userFromDb2.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));

		List<User> users = userRepository.findAll();
		List<Demand> demands = demandRepository.findAll();

		assertEquals(2, users.size());
		assertEquals(2, demands.size());

		userRepository.delete(userFromDb2);
		Demand demandFromDb = demandRepository.findByName("Demand2").orElse(null);
		assert demandFromDb != null;

		users = userRepository.findAll();
		demands = demandRepository.findAll();

		assertEquals(1, users.size());
		assertEquals(2, demands.size());
		assertNull(demandFromDb.getUserId());
	}

	//Expected: user_id will be null by both demands
	@Test
	public void testDeleteUserWithMultipleProjects() {
		projectRepository.deleteAll();
		userRepository.save(user);
		projectRepository.save(new Project("Test project 1", "SAP Number 1", "phase", "status", "order", "project"));
		projectRepository.save(new Project("Test project 2", "SAP Number 2", "phase", "status", "order", "project"));
		User userFromDb = userRepository.findByUsername("Test User").orElse(null);
		Project projectFromDb1 = projectRepository.findByProjectName("Test project 1").orElse(null);
		Project projectFromDb2 = projectRepository.findByProjectName("Test project 2").orElse(null);
		assert userFromDb != null;
		assert projectFromDb1 != null;
		assert projectFromDb2 != null;

		demandRepository.save(new Demand("Demand1", "mib1", projectFromDb1.getId(), userFromDb.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));
		demandRepository.save(new Demand("Demand2", "mib2", projectFromDb2.getId(), userFromDb.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1)));

		List<User> users = userRepository.findAll();
		List<Project> projects = projectRepository.findAll();
		List<Demand> demands = demandRepository.findAll();

		assertEquals(1, users.size());
		assertEquals(2, projects.size());
		assertEquals(2, demands.size());

		userRepository.delete(userFromDb);
		Demand demandFromDb1 = demandRepository.findByName("Demand1").orElse(null);
		Demand demandFromDb2 = demandRepository.findByName("Demand2").orElse(null);
		assert demandFromDb1 != null;
		assert demandFromDb2 != null;

		users = userRepository.findAll();
		projects = projectRepository.findAll();
		demands = demandRepository.findAll();

		assertEquals(0, users.size());
		assertEquals(2, projects.size());
		assertEquals(2, demands.size());

		assertNull(demandFromDb1.getUserId());
		assertNull(demandFromDb2.getUserId());
	}

	private Demand updateDemandFields(Demand demand) {
		userRepository.save(user);
		projectRepository.save(new Project("Test project 2", "SAP Number 1", "phase", "status", "order", "project"));
		User userFromDb = userRepository.findByUsername("Test User").orElse(null);
		Project projectFromDb = projectRepository.findByProjectName("Test project 2").orElse(null);
		assert userFromDb != null;
		assert projectFromDb != null;
		demand.setName("Demand2");
		demand.setUtilization(100);
		demand.setMib("mib2");
		demand.setProjectStart(LocalDate.now());
		demand.setProjectEnd(LocalDate.now().plusMonths(1));
		demand.setProjectId(projectFromDb.getId());
		demand.setUserId(userFromDb.getId());
		demandRepository.save(demand);
		return demandRepository.findByName("Demand2").orElse(null);
	}
}
