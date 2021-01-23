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

import cronos.alice.model.entity.Project;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class ProjectRepositoryTest {

	@Autowired
	private ProjectRepository projectRepository;

	private Project project;

	@Before
	public void initData() {
		project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
	}

	@Test
	public void testSaveAndFind() {
		projectRepository.save(project);
		Project projectFromDb = projectRepository.findByProjectName("Test Project").orElse(null);
		assert projectFromDb != null;

		assertEquals("Test Project", projectFromDb.getProjectName());
		assertEquals("Active", projectFromDb.getPhase());
		assertEquals("Open", projectFromDb.getStatus());
		assertEquals("Fixed price with limit", projectFromDb.getOrderType());
		assertEquals("Development project", projectFromDb.getProjectType());
	}

	@Test
	public void testUpdate() {
		projectRepository.save(project);
		Project projectFromDb = projectRepository.findByProjectName("Test Project").orElse(null);
		assert projectFromDb != null;

		assertEquals("Test Project", projectFromDb.getProjectName());

		Project updatedProject = updateProjectFields(projectFromDb);
		assert updatedProject != null;

		assertEquals(projectFromDb.getId(), updatedProject.getId());
		assertEquals("F.1223433400", updatedProject.getSap());
		assertEquals("Manager", updatedProject.getManager());
		assertEquals("Backup Manager", updatedProject.getBackupManager());
		assertEquals("Customer", updatedProject.getCustomer());
		assertEquals("Business Unit Hu", updatedProject.getBuHu());
		assertEquals(LocalDate.now(), updatedProject.getStart());
		assertEquals(LocalDate.now().plusYears(1), updatedProject.getEnd());
		assertEquals(20, updatedProject.getFte());
		assertEquals("Owner", updatedProject.getOwner());
		assertEquals("Contact Person", updatedProject.getContactPerson());
	}

	@Test
	public void testDelete() {
		projectRepository.save(project);
		List<Project> projects = projectRepository.findAll();
		assertEquals(1, projects.size());

		projectRepository.delete(project);
		projects = projectRepository.findAll();
		assertEquals(0, projects.size());
	}

	private Project updateProjectFields(Project project) {
		project.setProjectName("Test Project 2");
		project.setPhase("Executing");
		project.setSap("F.1223433400");
		project.setStatus("Closed");
		project.setManager("Manager");
		project.setBackupManager("Backup Manager");
		project.setStart(LocalDate.now());
		project.setEnd(LocalDate.now().plusYears(1));
		project.setFte(20);
		project.setCustomer("Customer");
		project.setComment("Comment");
		project.setDescription("Description");
		project.setBu("Business Unit");
		project.setBuHu("Business Unit Hu");
		project.setOwner("Owner");
		project.setContactPerson("Contact Person");
		project.setOrderType("Order Type");
		project.setProjectType("Project Type");
		projectRepository.save(project);
		return projectRepository.findByProjectName("Test Project 2").orElse(null);
	}
}
