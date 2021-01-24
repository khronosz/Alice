import {Service} from "./Service";

class HomeService extends Service {

    getHome = (callback) => this.get("/", callback)

    update = (project, callback) => this.put("/", project, callback)
}

export default new HomeService();