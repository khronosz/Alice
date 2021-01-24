import React, {Component} from "react";

import HomeService from "../../services/HomeService";
import AuthService from "../../services/AuthService";
import {Home} from "./Home";

export default class HomeContainer extends Component {

    state = {
        content: "",
        user: AuthService.getCurrentUser(),
        project: {},
        projects: [],
        idx: 0,
        message: undefined,
        messageType: undefined,
        loading: true,
        hasChanged: false
    }

    componentDidMount() {
        this.findAllProjects();
    }


    findAllProjects = () => HomeService.getHome((response, error) => {
        if (response) {
            this.setState({projects: response})
            this.getProject(this.state.idx)
        }
        else this.setState({ content: error })
        this.setState({ loading: false })
    })

    getProject(idx) {
        this.setState({project: this.state.projects[idx]})
    }

    nextBtn = () => {
        this.setState({project: this.state.projects[this.state.idx + 1], idx: this.state.idx + 1})
    }

    prevBtn = () => {
        this.setState({project: this.state.projects[this.state.idx - 1], idx: this.state.idx - 1})
    }

    resetMessage = () => this.setState({message: undefined, messageType: undefined})

    updateProject = event => {
        event.preventDefault();
        HomeService.update(this.state.project, (response, error) => {
            if (response) {
                this.setState({ message: "Project Updated Successfully!", messageType: "success" });
                this.findAllProjects();
                this.getProject(this.state.idx)
            }
        })
    }

    resetProject = () => {
        this.findAllProjects();
        this.setState({ message: "Project Cancelled!", messageType: "error", hasChanged: false });
    }

    projectChange = event => {
        const { name, value } = event.target
        if (value !== this.state.projects[this.state.idx].customer &&
            value !== this.state.projects[this.state.idx].description &&
            value !== this.state.projects[this.state.idx].technology) {
            this.setState({ hasChanged: true })
        } else {
            this.setState({ hasChanged: false })
        }
        this.setState(prevState => ({
            project: {
                ...prevState.project,
                [name]: value
            }
        }));
    }

    render() {
        return (
            <Home data={{
                ...this.state,
                nextBtn: this.nextBtn,
                prevBtn: this.prevBtn,
                resetMessage: this.resetMessage,
                updateProject: this.updateProject,
                resetProject: this.resetProject,
                projectChange: this.projectChange
            }} />
        )
    }
}