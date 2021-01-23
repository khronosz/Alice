package cronos.alice.repository;

import java.util.List;

import com.querydsl.core.Tuple;
import cronos.alice.model.entity.Project;

public interface CustomizedProjectRepository {

	List<Project> findAllDtoBySearchText(String searchText);

	List<Long> findAllIds();

	List<Tuple> findAllProjectDashboardDto(Long userId);
}
