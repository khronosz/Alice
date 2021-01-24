import React, { Component } from "react";

import AuthService from "../../services/AuthService";
import { Profile } from "./Profile";
import UserService from "../../services/UserService";

export default class ProfileContainer extends Component {

    state = {
        currentUser: AuthService.getCurrentUser(),
        user: {},
        content: "",
        messageType: undefined,
        message: undefined
    }

    componentDidMount() {
        this.findUserById();
    }

    findUserById = () => {
        UserService.findById(this.state.currentUser.id, (response, error) => {
            if (!error) {
                this.setState({ user: response})
            }
        })
    }

    saveUser = event => {
        event.preventDefault();
        UserService.updateProfile(this.state.user, (response, error) => {
            if (!error) {
                this.setState({ message: "Data Updated Successfully!", messageType: "success" });
                this.findUserById();
            }
        })
    }

    resetUser = () => {
        this.findUserById();
    }

    userChange = event => {
       const { name, value } = event.target
        this.setState(prevState => ({
            user: {
                ...prevState.user,
                [name]: value
            }
        }));
    }

    resetMessage = () => this.setState({ message: undefined, messageType: undefined })

    render() {
        return (
            <Profile data={{
                ...this.state,
                resetMessage: this.resetMessage,
                saveUser: this.saveUser,
                resetUser: this.resetUser,
                userChange: this.userChange
            }} />
        );
    }
}