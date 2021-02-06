import { Service } from "./Service";

class UserService extends Service {

    findAll = (callback) => this.get("/team", callback)

    chkLastValidation = (id, callback) => this.put("/team/" + id, id, callback)

    findById = (id, callback) => this.get("/user/" + id, callback)

    update = (id, user, callback) => this.put("/user/" + id, user, callback)

    save = (user, callback) => this.post("/user", user, callback)

    delete = (id, callback) => this.del("/user/" + id, callback)

    findUtilPlanUsers = (callback) => this.get("/utilization", callback)

    findAllDirectManagerNames = (callback) => this.get("/directManagers", callback)

    findAllUsers = (callback) => this.get("/usernames", callback)

    updateProfile = (user, callback) => this.put("/profile", user, callback)

    changePassword = (passwordDto, callback) => this.put("/changePassword", passwordDto, callback)
}

export default new UserService();