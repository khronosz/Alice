import React, {Component} from "react";

import {Dashboard} from "./Dashboard"
import AuthService from "../../../services/AuthService";
import DashboardService from "../../../services/DashboardService";
import UserService from "../../../services/UserService";

export default class DashboardContainer extends Component {

    state = {
        currentUser: AuthService.getCurrentUser(),
        nonUtilizedUsers: [],
        nonValidatedUsers: [],
        projects: [],
        currentProjectPage: 1,
        currentUtilPage: 1,
        currentValidPage: 1,
        itemsPerPage: 5,
        content: "",
        messageType: undefined,
        message: undefined
    }

    componentDidMount() {
        this.getProfile();
    }

    getProfile = () => DashboardService.getDashboard((response, error) => {
        if (response) {
            this.setState({
                projects: response.projects,
                nonUtilizedUsers: response.nonUtilizedUsers,
                nonValidatedUsers: response.nonValidatedUsers
            })
        }
        else this.setState({ content: error.message })
    })

    setProjectPage = number => this.setState({currentProjectPage: number})

    setNonUtilizedPage = number => this.setState({currentUtilPage: number})

    setNonValidatedPage = number => this.setState({currentValidPage: number})

    updateProject = id => this.props.history.push("/project/" + id);

    editUser = id => this.props.history.push("/user/" + id);

    chkLastValidation = userId => {
        UserService.chkLastValidation(userId, (response, error) => {
            if (!error) {
                this.setState({ message: "User Validated Successfully!", messageType: "success" });
            }
            window.location.reload();
        })
    }

    editDemand = id => this.props.history.push("/user/" + id + "/demands");

    resetMessage = () => this.setState({ message: undefined, messageType: undefined })

    render() {
        return (
            <Dashboard data={{
                ...this.state,
                setProjectPage: this.setProjectPage,
                setNonUtilizedPage: this.setNonUtilizedPage,
                setNonValidatedPage: this.setNonValidatedPage,
                updateProject: this.updateProject,
                editUser: this.editUser,
                chkLastValidation: this.chkLastValidation,
                editDemand: this.editDemand,
                resetMessage: this.resetMessage
            }} />
        );
    }
}