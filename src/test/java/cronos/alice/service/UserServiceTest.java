package cronos.alice.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.dto.NonUtilizedUserDto;
import cronos.alice.model.dto.NonValidatedUserDto;
import cronos.alice.model.dto.TeamDto;
import cronos.alice.model.dto.UserDto;
import cronos.alice.model.dto.UtilPlanDto;
import cronos.alice.model.entity.Demand;
import cronos.alice.model.entity.Project;
import cronos.alice.model.entity.Role;
import cronos.alice.model.entity.User;
import cronos.alice.model.enums.user.ERole;
import cronos.alice.repository.RoleRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DemandService demandService;

	@Autowired
	private RoleRepository roleRepository;

	private User jane;
	private User jon;
	private User manager;

	@Before
	public void initData() {
		manager = new User("Direct Manager", "tester", "IT", "boss@test.com", "Los Angeles", "Architect");
		manager.getRoles().add(roleRepository.save(new Role(ERole.ROLE_USER)));
		userService.save(manager);
		jane = new User("Jane Doe", "tester", "IT", "jane@test.com", "Los Angeles", "Architect");
		jon = new User("Jon Doe", "tester", "IT", "jon@test.com", "Los Angeles", "Junior");
		jane.setDirectManagerId(manager.getId());
		jon.setDirectManagerId(manager.getId());
		userService.save(jane);
		userService.save(jon);
	}

	@Test
	public void testSaveAndFind() {
		User userFromDb = userService.findById(jane.getId());

		assertEquals("Jane Doe", userFromDb.getUsername());
		assertEquals("jane@test.com", userFromDb.getEmail());
	}

	@Test
	public void testFindAllUsernames() {
		List<String> usernames = userService.findAllUsernames();

		assertEquals(3, usernames.size());
		assertEquals(usernames, Arrays.asList("Direct Manager", "Jane Doe", "Jon Doe"));
	}

	@Test
	public void testFindAllDirectManagers() {
		List<String> managers = userService.findAllDirectManagers();

		assertEquals(1, managers.size());
		assertEquals("Direct Manager", managers.get(0));
	}

	@Test
	public void testFindAllUtilPlanDto() {
		Project project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		projectService.save(project);
		Demand demand1 = new Demand("Demand 1", "Mib 1", project.getId(), jane.getId(), 50, LocalDate.now(), LocalDate.now().plusMonths(1));
		Demand demand2 = new Demand("Demand 2", "Mib 2", project.getId(), jon.getId(), 100, LocalDate.now(), LocalDate.now().plusMonths(1));
		demandService.save(demand1);
		demandService.save(demand2);
		List<UtilPlanDto> dtoList = userService.findAllUtilPlanDto();

		assertEquals(2, dtoList.size());
		assertEquals("Direct Manager", dtoList.get(0).getUsername());
		assertEquals("Jane Doe", dtoList.get(1).getUsername());
		assertTrue(dtoList.get(0).getProjectNames().isEmpty());
		assertEquals("Test Project", dtoList.get(1).getProjectNames());
	}

	@Test
	public void testFindAllTeamDto() {
		List<TeamDto> dtoList = userService.findAllTeamDto(manager.getId());

		assertEquals(2, dtoList.size());
		assertEquals("Jane Doe", dtoList.get(0).getUsername());
		assertEquals("Jon Doe", dtoList.get(1).getUsername());
	}

	@Test
	public void testGetNonUtilizedUsers() {
		Project project = new Project("Test Project", "F.12234334", "Active", "Open", "Fixed price with limit", "Development project");
		projectService.save(project);
		Demand demand = new Demand("Demand 1", "Mib 1", project.getId(), jane.getId(), 100, LocalDate.now(), LocalDate.now().plusMonths(1));
		demandService.save(demand);
		List<NonUtilizedUserDto> dtoList = userService.getNonUtilizedUsers(manager.getId());

		assertEquals(1, dtoList.size());
		assertEquals("Jon Doe", dtoList.get(0).getUsername());
	}

	@Test
	public void testGetNonValidatedUsers() {
		List<NonValidatedUserDto> dtoList = userService.getNonValidatedUsers(manager.getId());

		assertEquals(2, dtoList.size());

		jane.setLastValidation(LocalDate.now());
		userService.save(jane);
		dtoList = userService.getNonValidatedUsers(manager.getId());

		assertEquals(1, dtoList.size());
	}

	@Test
	public void testFindTeamDtoById() {
		TeamDto dto = userService.findTeamDtoById(jane.getId());

		assertEquals(dto.getId(), jane.getId());
		assertEquals(dto.getUsername(), jane.getUsername());
		assertEquals(dto.getJob(), jane.getJob());
		assertEquals(dto.getDepartment(), jane.getDepartment());
		assertEquals(dto.getEmail(), jane.getEmail());
		assertEquals(dto.getCity(), jane.getCity());
		assertEquals(dto.getLevel(), jane.getLevel());
		assertEquals(dto.getNotes(), jane.getNotes());
		assertEquals(dto.getLastValidation(), jane.getLastValidation());
	}

//	@Test
//	public void testConvertToEntity() {
//		UserDto dto = new UserDto(jane);
//		dto.setDirectManagerName(manager.getUsername());
//		User user = userService.convertToEntity(dto);
//
//		assertEquals(user.getId(), jane.getId());
//		assertEquals(user.getDirectManagerId(), manager.getId());
//	}

	@Test
	public void testConvertToDto() {
		UserDto dto = userService.convertToDto(jane);

		assertEquals(dto.getId(), jane.getId());
		assertEquals(dto.getUsername(), jane.getUsername());
		assertEquals(dto.getJob(), jane.getJob());
		assertEquals(dto.getDepartment(), jane.getDepartment());
		assertEquals(dto.getEmail(), jane.getEmail());
		assertEquals(dto.getCity(), jane.getCity());
		assertEquals(dto.getLevel(), jane.getLevel());
		assertEquals(dto.getDirectManagerId(), jane.getDirectManagerId());
		assertEquals(dto.getDirectManagerName(), userService.findById(jane.getDirectManagerId()).getUsername());
		assertEquals(dto.getNotes(), jane.getNotes());
		assertEquals(dto.getLastValidation(), jane.getLastValidation());
		assertEquals(dto.getLastLogin(), jane.getLastLogin());
	}

	@Test
	public void testDeleteById() {
		List<TeamDto> dtoList = userService.findAllTeamDto(manager.getId());

		assertEquals(2, dtoList.size());
		assertEquals("Jane Doe", dtoList.get(0).getUsername());
		assertEquals("Jon Doe", dtoList.get(1).getUsername());

		userService.deleteById(jon.getId());
		dtoList = userService.findAllTeamDto(manager.getId());

		assertEquals(1, dtoList.size());
		assertEquals("Jane Doe", dtoList.get(0).getUsername());
	}
}
