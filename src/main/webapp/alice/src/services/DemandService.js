import {Service} from "./Service";

class DemandService extends Service {

	findAll = (projectId, callback) => this.get("/project/" + projectId + "/demands", callback)

	findById = (projectId, demandId, callback) => this.get("/project/" + projectId + "/demand/" + demandId, callback)

	update = (projectId, id, demand, callback) => this.put("/project/" + projectId + "/demand/" + id, demand, callback)

	save = (projectId, demand, callback) => this.post("/project/" + projectId + "/demand", demand, callback)

	delete = (projectId, demandId, callback) => this.del("/project/" + projectId + "/demand/" + demandId, callback)

	findAllByUser = (userId, callback) => this.get("/user/" + userId + "/demands", callback)

	// updateFromUser = (userId, demandId, demand, callback) => this.put("/user/" + userId + "/demand/" + demandId, demand, callback)
}

export default new DemandService();