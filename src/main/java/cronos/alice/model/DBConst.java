package cronos.alice.model;

import cronos.alice.model.entity.QDemand;
import cronos.alice.model.entity.QProject;
import cronos.alice.model.entity.QUser;

public class DBConst {

	public static final QDemand demand = QDemand.demand;
	public static final QProject project = QProject.project;
	public static final QUser user = QUser.user;

	private DBConst() {
	}
}
