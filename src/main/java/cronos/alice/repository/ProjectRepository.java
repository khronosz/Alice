package cronos.alice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cronos.alice.model.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, CustomizedProjectRepository {

    Optional<Project> findByProjectName(String projectName);
}
