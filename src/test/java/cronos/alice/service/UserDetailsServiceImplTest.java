package cronos.alice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import cronos.alice.model.entity.User;
import cronos.alice.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class UserDetailsServiceImplTest {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void initData() {
		userRepository.save(new User("Jane Doe", "tester", "IT", "johny@test.com", "Los Angeles", "Architect"));
	}

	@Test
	public void testLoadUserByUsername() {
		userDetailsService.loadUserByUsername("Jane Doe");
	}
}
