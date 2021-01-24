import {Service} from "./Service";

class AuthService extends Service {

	login = (username, password, callback) => this.post("/login", {username, password}, callback)

	logout = () => localStorage.removeItem("user");

	getCurrentUser = () => JSON.parse(localStorage.getItem('user'))

	header = () => {
		const user = new AuthService().getCurrentUser()
		if (user && user.accessToken) return {headers: {Authorization: 'Bearer ' + user.accessToken}};
		else return {headers: {}};
	}
}

export default new AuthService();