import React, { Component } from "react";
import AuthService from "../../services/AuthService";
import { Login } from "./Login";

export default class LoginContainer extends Component {

    state = {
        username: "",
        password: "",
        lastLogin: "",
        message: ""
    }

    userChange = event => {
        const { name, value } = event.target
        this.setState({ [name]: value });
    }

    login = (event) => {
        event.preventDefault();
        AuthService.login(this.state.username, this.state.password, (response, error) => {
            if (response && response.accessToken) {
                localStorage.setItem("user", JSON.stringify(response));
                if (response.roles.includes("ROLE_ADMIN")) {
                    this.props.history.push("/admin");
                    window.location.reload();
                }
                if (response.roles.includes("ROLE_MODERATOR")) {
                    this.props.history.push("/moderator");
                    window.location.reload();
                }
                if (response.roles.includes("ROLE_USER")) {
                    this.props.history.push("/dashboard");
                    window.location.reload();
                }
            } else this.setState({ message: error })
        })
    }

    render() {
        return (
            <Login data={{
                ...this.state,
                userChange: this.userChange,
                login: this.login
            }} />
        );
    }
}