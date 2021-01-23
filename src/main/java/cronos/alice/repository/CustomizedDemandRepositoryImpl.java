package cronos.alice.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import static cronos.alice.model.DBConst.demand;
import static cronos.alice.model.DBConst.project;
import static cronos.alice.model.DBConst.user;

public class CustomizedDemandRepositoryImpl implements CustomizedDemandRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tuple> findAllDtoByUserId(final Long userId) {
		return selectDemand()
				.where(demand.userId.eq(userId))
				.orderBy(demand.name.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllDtoByProjectId(final Long projectId) {
		return selectDemand()
				.where(demand.projectId.eq(projectId))
				.orderBy(demand.name.asc())
				.fetch();
	}

	//todo: demandId != demand.id
	@Override
	public Integer getTotalUtilizationByUser(final Long userId, final Long demandId) {
		return new JPAQuery<>(entityManager)
				.select(demand.utilization.sum())
				.from(demand)
				.where(demand.userId.eq(userId).and(demand.id.isNull()))
				.fetchFirst();
	}

	@Override
	public List<Tuple> findAllDto() {
		return selectDemand()
				.orderBy(demand.name.asc())
				.fetch();
	}

	@Override
	public Tuple findDtoById(Long id) {
		return selectDemand()
				.where(demand.id.eq(id))
				.fetchFirst();
	}

	private JPAQuery<Tuple> selectDemand() {
		return new JPAQuery<>(entityManager)
				.select(demand.id,
						demand.name,
						demand.mib,
						demand.userId,
						user.username,
						demand.projectId,
						project.projectName,
						demand.utilization,
						demand.projectStart,
						demand.projectEnd)
				.from(demand)
				.leftJoin(user)
				.on(demand.userId.eq(user.id))
				.leftJoin(project)
				.on(demand.projectId.eq(project.id));
	}
}
