package cronos.alice.repository;

import java.util.List;

import com.querydsl.core.Tuple;

public interface CustomizedUserRepository {

	List<Tuple> findAllTeamDto(Long directManagerId);

	List<Tuple> findAllUtilPlanDto();

	List<Tuple> initProjectNamesForDto();

	List<Tuple> findAllNonUtilizedUserDto(Long userId);

	List<Tuple> findAllNonValidatedUserDto(Long userId);

	Tuple findTeamDtoById(Long id);
}
