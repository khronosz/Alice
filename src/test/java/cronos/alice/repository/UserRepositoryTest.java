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

import cronos.alice.model.entity.Role;
import cronos.alice.model.entity.User;
import cronos.alice.model.enums.user.ERole;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	private User user;
	private User manager;

	@Before
	public void initData() {
		user = new User("Jane Doe", "tester", "IT", "johny@test.com", "Los Angeles", "Architect");
		manager = new User("Direct Manager", "tester", "IT", "boss@test.com", "Los Angeles", "Architect");
	}

	@Test
	public void testSaveAndFind() {
		userRepository.save(user);
		User userFromDb = userRepository.findByUsername("Jane Doe").orElse(null);

		assert userFromDb != null;
		assertEquals("Jane Doe", userFromDb.getUsername());
		assertEquals("Architect", userFromDb.getLevel());
	}

	@Test
	public void testUpdate() {
		userRepository.save(manager);
		User managerFromDb = userRepository.findByUsername("Direct Manager").orElse(null);
		assert managerFromDb != null;

		user.setDirectManagerId(managerFromDb.getId());
		userRepository.save(user);
		User userFromDb = userRepository.findByUsername("Jane Doe").orElse(null);
		assert userFromDb != null;

		assertEquals("Jane Doe", userFromDb.getUsername());
		assertEquals(managerFromDb.getId(), userFromDb.getDirectManagerId());

		User updatedUser = updateUserFields(userFromDb);
		assert updatedUser != null;

		assertEquals(userFromDb.getId(), updatedUser.getId());
		assertEquals("Jack Doe", updatedUser.getUsername());
		assertEquals(LocalDate.now(), updatedUser.getLastValidation());
		assertEquals(LocalDate.now(), updatedUser.getLastLogin());
		assertEquals("jack@doe.com", updatedUser.getEmail());
		assertEquals("Developer", updatedUser.getJob());
		assertEquals("HR", updatedUser.getDepartment());
		assertEquals("California", updatedUser.getCity());
		assertEquals("Notes", updatedUser.getNotes());
		assertEquals("Junior", updatedUser.getLevel());
		User bossFromDb = userRepository.findByUsername("Direct Manager 2").orElse(null);
		assert bossFromDb != null;
		assertEquals(bossFromDb.getId(), updatedUser.getDirectManagerId());
	}

	@Test
	public void testDelete() {
		userRepository.save(user);
		List<User> users = userRepository.findAll();
		assertEquals(1, users.size());

		userRepository.delete(user);
		users = userRepository.findAll();
		assertEquals(0, users.size());
	}

	@Test
	public void testAssignDirectManager() {
		userRepository.save(manager);
		User managerFromDb = userRepository.findByUsername("Direct Manager").orElse(null);
		assert managerFromDb != null;

		user.setDirectManagerId(managerFromDb.getId());
		userRepository.save(user);

		User userFromDb = userRepository.findByUsername("Jane Doe").orElse(null);
		assert userFromDb != null;

		assertEquals(managerFromDb.getId(), userFromDb.getDirectManagerId());
	}

	@Test
	public void testAssignRole() {
		user.getRoles().add(roleRepository.save(new Role(ERole.ROLE_USER)));
		user.getRoles().add(roleRepository.save(new Role(ERole.ROLE_MODERATOR)));
		userRepository.save(user);
		Role roleUser = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
		Role roleModerator = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(null);
		assert roleUser != null;
		assert roleModerator != null;

		List<Role> roles = roleRepository.findAll();

		assertEquals(2, user.getRoles().size());
		assertEquals(2, roles.size());
		assertEquals(ERole.ROLE_USER, roleUser.getName());
		assertEquals(ERole.ROLE_MODERATOR, roleModerator.getName());
	}

	private User updateUserFields(User user) {
		userRepository.save(new User("Direct Manager 2", "tester", "IT", "newboss@test.com", "Los Angeles", "Architect"));
		user.setUsername("Jack Doe");
		user.setEmail("jack@doe.com");
		user.setJob("Developer");
		user.setDepartment("HR");
		user.setCity("California");
		user.setNotes("Notes");
		user.setLevel("Junior");
		user.setLastValidation(LocalDate.now());
		user.setLastLogin(LocalDate.now());
		User managerFromDb = userRepository.findByUsername("Direct Manager 2").orElse(null);
		assert managerFromDb != null;
		user.setDirectManagerId(managerFromDb.getId());
		userRepository.save(user);
		return userRepository.findByUsername("Jack Doe").orElse(null);
	}
}
