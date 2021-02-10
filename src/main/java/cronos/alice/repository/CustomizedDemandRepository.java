package cronos.alice.repository;

import java.util.List;

import com.querydsl.core.Tuple;

public interface CustomizedDemandRepository {

	List<Tuple> findAllDtoByUserId(Long userId);

	List<Tuple> findAllDtoByProjectId(Long projectId);

	Integer getTotalUtilizationByUser(Long userId, Long demandId);

	Integer getTotalUtilizationByUser(Long userId);

	List<Tuple> findAllDto();

	Tuple findDtoById(Long id);

}
