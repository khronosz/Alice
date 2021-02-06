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
import cronos.alice.model.dto.ProjectDto;
import cronos.alice.model.entity.Project;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class ProjectServiceTest {

	@Autowired
	private ProjectService projectService;

	private Project project;

	@Before
	public void initData() {
		project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
	}

	@Test
	public void testSaveAndFind() {
		project.setContactPerson("John Wick");
		projectService.save(project);
		Project projectFromDb = projectService.findById(project.getId());

		assertEquals("Test Project", projectFromDb.getProjectName());
		assertEquals("Fixed price with limit", projectFromDb.getOrderType());
		assertEquals("John Wick", projectFromDb.getContactPerson());
	}

	@Test
	public void testFindAll() {
		Project project1 = new Project("Test Project 1", "F.12234335", "Active", "Open", "Fixed price with limit", "Development project");
		projectService.save(project);
		projectService.save(project1);

		List<ProjectDto> dtoList = projectService.findAll();

		assertEquals(2, dtoList.size());
		assertEquals("Test Project 1", dtoList.get(1).getProjectName());
	}

	@Test
	public void testFindAllBySearchText() {
		Project project1 = new Project("Test Project 1", "F.12234335", "Active", "Open", "Fixed price with limit", "Development project");
		Project project2 = new Project("Test Project 2", "F.12234558", "Active", "Closed", "Fixed price with limit", "Development project");
		projectService.save(project1);
		projectService.save(project2);

		List<ProjectDto> dtoList = projectService.findAllDtoBySearchText("closed");

		assertEquals(1, dtoList.size());

		Project project3 = new Project("Test Project 3", "F.99885421", "Active", "In Progress", "Fixed price with limit", "Development project");
		project3.setDescription("closed");
		projectService.save(project3);

		dtoList = projectService.findAllDtoBySearchText("closed");

		assertEquals(2, dtoList.size());
	}

	@Test
	public void testFindAllIds() {
		Project project1 = new Project("Test Project 1", "F.12234335", "Active", "Open", "Fixed price with limit", "Development project");
		Project project2 = new Project("Test Project 2", "F.12234558", "Active", "Closed", "Fixed price with limit", "Development project");
		projectService.save(project1);
		projectService.save(project2);

		List<Long> list = projectService.findAllIds();

		assertEquals(2, list.size());
		assertEquals(project1.getId(), list.get(0));
		assertEquals(project2.getId(), list.get(1));
	}

	@Test
	public void testFindAllProjectDescriptionDto() {
		Project project1 = new Project("Test Project 1", "F.12234335", "Active", "Open", "Fixed price with limit", "Development project");
		Project project2 = new Project("Test Project 2", "F.12234558", "Active", "Closed", "Fixed price with limit", "Development project");
		project1.setLongDescription("project 1 desc");
		project2.setCustomer("project 2 customer");
		projectService.save(project1);
		projectService.save(project2);

		List<ProjectDescriptionDto> dtoList = projectService.findAllProjectDescriptionDto();

		assertEquals(2, dtoList.size());
		assertEquals(project1.getLongDescription(), dtoList.get(0).getDescription());
		assertEquals(project2.getCustomer(), dtoList.get(1).getCustomer());
	}

//	@Test
//	public void testConvertToEntity() {
//		ProjectDto dto = new ProjectDto(project);
//		Project projectConverted = projectService.convertToEntity(dto);
//
//		assertEquals(projectConverted.getId(), project.getId());
//		assertEquals(projectConverted.getId(), dto.getId());
//		assertEquals(projectConverted.getProjectName(), project.getProjectName());
//		assertEquals(projectConverted.getProjectName(), dto.getProjectName());
//		assertEquals(projectConverted.getSap(), project.getSap());
//		assertEquals(projectConverted.getSap(), dto.getSap());
//	}


	@Test
	public void testConvertToDto() {
		ProjectDto dto = projectService.convertToDto(project);

		assertEquals(dto.getId(), project.getId());
		assertEquals(dto.getProjectName(), project.getProjectName());
		assertEquals(dto.getStatus(), project.getStatus());
	}

	@Test
	public void testDeleteById() {
		Project project1 = new Project("Test Project 1", "F.12234335", "Active", "Open", "Fixed price with limit", "Development project");
		Project project2 = new Project("Test Project 2", "F.12234558", "Active", "Closed", "Fixed price with limit", "Development project");
		projectService.save(project1);
		projectService.save(project2);

		List<ProjectDto> projectList = projectService.findAll();

		assertEquals(2, projectList.size());

		projectService.deleteById(project1.getId());
		projectList = projectService.findAll();

		assertEquals(1, projectList.size());
		assertEquals(project2.getId(), projectList.get(0).getId());
		assertEquals(project2.getProjectName(), projectList.get(0).getProjectName());
	}
}
