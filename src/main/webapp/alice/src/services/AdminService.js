import { Service } from "./Service";

class AdminService extends Service {

  findAllUsers = (callback) => this.get("/admin", callback)

}

export default new AdminService();