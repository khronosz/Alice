package cronos.alice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class DemandServiceTest {

	@Autowired
	private DemandService demandService;

	@Autowired
	private ProjectService projectService;

	private Demand demand1;
	private Demand demand2;
	private Project project;

	@Before
	public void initData() {
		project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		projectService.save(project);
		demand1 = new Demand("Demand1", "MIB1", project.getId());
		demand2 = new Demand("Demand2", "MIB2", project.getId());
	}

	@Test
	public void testSaveAndFind() {
		demandService.save(demand1);
		demandService.save(demand2);

		Demand demandFromDb = demandService.findById(demand1.getId());

		assertEquals(demandFromDb.getId(), demand1.getId());
		assertEquals("Demand1", demandFromDb.getName());
	}
}
