package cronos.alice.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.dto.ProjectDescriptionDto;
import cronos.alice.model.entity.Project;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class HomeServiceTest {

	@Autowired
	private HomeService homeService;

	@Autowired
	private ProjectService projectService;

	@Before
	public void initData() {
		Project project1 = new Project("Test Project 1", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		Project project2 = new Project("Test Project 2", "F876576884", "Active", "Open", "Fixed price with limit", "Development project");
		project1.setCustomer("Customer 1");
		project2.setCustomer("Customer 2");
		project1.setLongDescription("Long desc 1");
		project2.setLongDescription("Long desc 2");
		project1.setTechnology("Technology 1");
		project2.setTechnology("Technology 2");
		projectService.save(project1);
		projectService.save(project2);
	}

	@Test
	public void testFindAllProjectDescriptionDto() {
		List<ProjectDescriptionDto> dtoList = homeService.findAllProjectDescriptionDto();

		assertEquals(2, dtoList.size());
		assertEquals("Test Project 1", dtoList.get(0).getProjectName());
		assertEquals("Test Project 2", dtoList.get(1).getProjectName());
		assertEquals("Customer 1", dtoList.get(0).getCustomer());
		assertEquals("Customer 2", dtoList.get(1).getCustomer());
		assertEquals("Long desc 1", dtoList.get(0).getDescription());
		assertEquals("Long desc 2", dtoList.get(1).getDescription());
		assertEquals("Technology 1", dtoList.get(0).getTechnology());
		assertEquals("Technology 2", dtoList.get(1).getTechnology());
	}

	@Test
	public void testConvertAndSave() {
		List<ProjectDescriptionDto> dtoList = homeService.findAllProjectDescriptionDto();

		assertEquals(2, dtoList.size());
		assertEquals("Technology 1", dtoList.get(0).getTechnology());

		Project project = homeService.convertToEntity(dtoList.get(0));
		project.setTechnology("New Technology");
		homeService.save(project);
		dtoList = homeService.findAllProjectDescriptionDto();

		assertEquals(2, dtoList.size());
		assertEquals("New Technology", dtoList.get(0).getTechnology());
	}

	@Test
	public void testConvertToDto() {
		Project project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		project.setCustomer("Customer");
		project.setLongDescription("Description");
		project.setTechnology("Technology");
		ProjectDescriptionDto dto = homeService.convertToDto(project);

		assertEquals(dto.getProjectName(), project.getProjectName());
		assertEquals(dto.getCustomer(), project.getCustomer());
		assertEquals(dto.getDescription(), project.getLongDescription());
		assertEquals(dto.getTechnology(), project.getTechnology());
	}
}
