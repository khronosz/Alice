import {Service} from "./Service";

class ProjectService extends Service {

    findAll = (callback) => this.get("/projects", callback)

    // eslint-disable-next-line no-useless-concat
    findAllBySearchText = (searchText, callback) => this.get("/projects/search" + "?searchText=" + searchText, callback)

    findById = (id, callback) => this.get("/project/" + id, callback)

    update = (id, project, callback) => this.put("/project/" + id, project, callback)

    save = (project, callback) => this.post("/project", project, callback)

    delete = (id, callback) => this.del("/project/" + id, callback)

    findAllId = (callback) => this.get("/projects/ids", callback)
}

export default new ProjectService();