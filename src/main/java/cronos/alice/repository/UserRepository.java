package cronos.alice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cronos.alice.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository {

	Optional<User> findByUsername(String username);

	@Query("select u.username from User u join u.roles r")
	List<String> findAllDirectManagerNames();

	@Query("Select u.username from User u order by u.username asc")
	List<String> findAllUsernames();

}
