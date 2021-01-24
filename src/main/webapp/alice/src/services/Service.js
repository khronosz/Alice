import axios from "axios";
import {API_URL} from "../components/App";
import AuthService from "./AuthService";

export class Service {

	get(url, callback) {
		axios.get(API_URL + url, AuthService.header())
			.then(response => callback(response.data))
			.catch(error => callback(undefined, this.getErrorMessage(error)))
	}

	post(url, object, callback) {
		axios.post(API_URL + url, object, AuthService.header())
			.then(response => callback(response.data))
			.catch(error => callback(undefined, this.getErrorMessage(error)))
	}

	put(url, object, callback) {
		axios.put(API_URL + url, object, AuthService.header())
			.then(response => callback(response.data))
			.catch(error => callback(undefined, this.getErrorMessage(error)))
	}

	del(url, callback) {
		axios.delete(API_URL + url, AuthService.header())
			.then(response => callback(response.data))
			.catch(error => callback(undefined, this.getErrorMessage(error)))
	};

	getErrorMessage = (error) => {
		let msg = error.response && error.response.data
		if (msg && msg.message) return msg.message
		return msg;
	}
}