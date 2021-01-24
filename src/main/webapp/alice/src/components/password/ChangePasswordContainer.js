import React, {Component} from "react";
import {ChangePassword} from "./ChangePassword";
import AuthService from "../../services/AuthService";
import UserService from "../../services/UserService";

export default class ChangePasswordContainer extends Component {

    state = {
        currentUser: AuthService.getCurrentUser(),
        passwordDto: {
            userId: "",
            currentPassword: "",
            newPassword: "",
            confirmPassword: ""
        },
        error: undefined,
        message: undefined,
        messageType: undefined
    }

    componentDidMount() {
        this.setState(prevState => {
            let passwordDto = {...prevState.passwordDto};
            passwordDto.userId = this.state.currentUser.id;
            return {passwordDto};
        })
    }

    changePassword = event => {
        event.preventDefault();
        UserService.changePassword(this.state.passwordDto, (response, error) => {
            if (!error) {
                this.setState({error: undefined, message: "Password changed successfully!", messageType: "success"})
                setTimeout(() => this.toProfile(), 500);
            } else this.setState({ error: error })
        })
    }

    inputChange = event => {
        const {name, value} = event.target
        this.setState(prevState => ({
            passwordDto: {
                ...prevState.passwordDto,
                [name]: value
            }
        }));
    }

    toProfile = () => this.props.history.push("/profile")

    render() {
        return (
            <ChangePassword data={{
                ...this.state,
                changePassword: this.changePassword,
                inputChange: this.inputChange,
                toProfile: this.toProfile,
                resetMessage: this.resetMessage
            }}/>
        );
    }
}