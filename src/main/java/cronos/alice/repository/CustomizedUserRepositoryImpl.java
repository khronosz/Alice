package cronos.alice.repository;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import cronos.alice.model.entity.QUser;

import static cronos.alice.model.DBConst.demand;
import static cronos.alice.model.DBConst.project;
import static cronos.alice.model.DBConst.user;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tuple> findAllTeamDto(Long directManagerId) {
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.department,
						user.email,
						user.city,
						user.level,
						demand.utilization.sum(),
						demand.projectEnd.min(),
						user.notes,
						user.lastValidation)
				.from(user)
				.leftJoin(demand)
				.on(demand.userId.eq(user.id))
				.where(user.directManagerId.eq(directManagerId))
				.groupBy(user.username)
				.orderBy(user.username.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllTeamDtoForAdmin() {
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.department,
						user.email,
						user.city,
						user.level,
						demand.utilization.sum(),
						demand.projectEnd.min(),
						user.notes,
						user.lastValidation)
				.from(user)
				.leftJoin(demand)
				.on(demand.userId.eq(user.id))
				.groupBy(user.username)
				.orderBy(user.username.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllUtilPlanDto() {
		QUser directManager = new QUser("directManager");
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.department,
						user.level,
						user.directManagerId,
						directManager.username,
						demand.utilization.sum(),
						demand.projectEnd.min(),
						user.notes)
				.from(user)
				.leftJoin(demand)
				.on(demand.userId.eq(user.id))
				.leftJoin(directManager)
				.on(user.directManagerId.eq(directManager.id))
				.groupBy(user.username)
				.having(demand.utilization.sum().lt(100).or(demand.utilization.sum().isNull()))
				.orderBy(user.username.asc())
				.fetch();
	}

	@Override
	public List<Tuple> initProjectNamesForDto() {
		return new JPAQuery<>(entityManager)
				.select(demand.userId, project.projectName)
				.from(demand)
				.leftJoin(project)
				.on(demand.projectId.eq(project.id))
				.orderBy(project.projectName.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllNonUtilizedUserDto(Long userId) {
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.level,
						demand.utilization.sum())
				.from(user)
				.leftJoin(demand)
				.on(demand.userId.eq(user.id))
				.where(user.directManagerId.eq(userId))
				.groupBy(user.username)
				.having(demand.utilization.sum().lt(100).or(demand.utilization.sum().isNull()))
				.orderBy(user.username.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllNonValidatedUserDto(final Long userId) {
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.level,
						user.lastValidation)
				.from(user)
				.where(user.directManagerId.eq(userId).and(user.lastValidation.isNull().or(user.lastValidation.before(LocalDate.now().minusDays(90)))))
				.orderBy(user.username.asc())
				.fetch();
	}

	@Override
	public Tuple findTeamDtoById(final Long id) {
		return new JPAQuery<>(entityManager)
				.select(user.id,
						user.username,
						user.job,
						user.department,
						user.email,
						user.city,
						user.level,
						demand.utilization.sum(),
						demand.projectEnd.min(),
						user.notes,
						user.lastValidation)
				.from(user)
				.leftJoin(demand)
				.on(demand.userId.eq(user.id))
				.where(user.id.eq(id))
				.groupBy(user.username)
				.orderBy(user.username.asc())
				.fetchFirst();
	}
}
