package cronos.alice.repository;

import cronos.alice.model.entity.Role;
import cronos.alice.model.enums.user.ERole;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	private Role role;

	@Before
	public void initData() {
		role = new Role(ERole.ROLE_ADMIN);
	}

	@Test
	public void testSaveAndFind() {
		roleRepository.save(role);
		Role roleFromDb = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
		assert roleFromDb != null;

		assertEquals(ERole.ROLE_ADMIN, roleFromDb.getName());
	}

	@Test
	public void testUpdate() {
		roleRepository.save(new Role(ERole.ROLE_USER));
		Role roleFromDb = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
		assert roleFromDb != null;

		assertEquals(ERole.ROLE_USER, roleFromDb.getName());

		roleFromDb.setName(ERole.ROLE_MODERATOR);
		roleRepository.save(roleFromDb);
		Role updatedRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(null);
		assert updatedRole != null;

		assertEquals(roleFromDb.getId(), updatedRole.getId());
		assertEquals(ERole.ROLE_MODERATOR, updatedRole.getName());
	}

	@Test
	public void testDelete() {
		roleRepository.save(role);
		List<Role> roles = roleRepository.findAll();
		assertEquals(1, roles.size());

		roleRepository.delete(role);
		roles = roleRepository.findAll();
		assertEquals(0, roles.size());
	}
}
