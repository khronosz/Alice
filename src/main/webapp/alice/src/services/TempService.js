import axios from 'axios';
import AuthService from "./AuthService";

const API_URL = 'http://localhost:8080/';

class TempService {
	getModeratorBoard() {
		return axios.get(API_URL + "moderator", AuthService.header());
	}

	getAdminBoard() {
		return axios.get(API_URL + "admin", AuthService.header());
	}
}

export default new TempService()