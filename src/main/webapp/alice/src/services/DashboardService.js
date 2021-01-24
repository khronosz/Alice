import { Service } from "./Service";

class DashboardService extends Service {

    getDashboard = (callback) => this.get("/dashboard", callback)
}

export default new DashboardService();