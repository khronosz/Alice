package cronos.alice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/db/clear.sql")
public class LoginServiceTest {

	@Test
	public void testLogin() {

	}
}
