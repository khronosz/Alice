package cronos.alice.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import cronos.alice.model.entity.Project;

import static cronos.alice.model.DBConst.demand;
import static cronos.alice.model.DBConst.project;
import static cronos.alice.model.DBConst.user;

public class CustomizedProjectRepositoryImpl implements CustomizedProjectRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Project> findAllDtoBySearchText(final String searchText) {
		return new JPAQuery<>(entityManager)
				.select(project)
				.from(project)
				.where(project.projectName.like("%" + searchText + "%")
						.or(project.phase.like("%" + searchText + "%"))
						.or(project.status.like("%" + searchText + "%"))
						.or(project.orderType.like("%" + searchText + "%"))
						.or(project.projectType.like("%" + searchText + "%"))
						.or(project.description.like("%" + searchText + "%"))
						.or(project.comment.like("%" + searchText + "%")))
				.orderBy(project.projectName.asc())
				.fetch();
	}

	@Override
	public List<Long> findAllIds() {
		return new JPAQuery<>(entityManager)
				.select(project.id)
				.from(project)
				.orderBy(project.id.asc())
				.fetch();
	}

	@Override
	public List<Tuple> findAllProjectDashboardDto(Long userId) {
		return new JPAQuery<>(entityManager)
				.select(project.id,
						project.projectName,
						project.phase,
						project.status,
						project.manager,
						project.customer,
						project.fte,
						project.end)
				.from(project)
				.where(project.id.in(JPAExpressions
						.select(demand.projectId).from(demand).where(demand.userId.in(JPAExpressions
						.select(user.id).from(user).where(user.directManagerId.eq(userId))))))
				.orderBy(project.projectName.asc())
				.fetch();
	}
}
