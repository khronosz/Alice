import React from 'react';
import AuthService from "../../services/AuthService";
import Project from "./Project";
import ProjectService from "../../services/ProjectService";

class ProjectContainer extends React.Component {

    state = {
        id: this.props.match.params.id,
        content: "",
        currentUser: AuthService.getCurrentUser(),
        project: {},
        message: undefined,
        error: undefined
    }

    componentDidMount() {
        if (this.state.id) this.findProject(this.state.id)
    }

    findProject = (id) => ProjectService.findById(id, (response, error) => {
        if (!error) this.setState({ project: response })
        else this.toProjects()
    })

    saveProject = (event) => {
        event.preventDefault()
        ProjectService.save(this.state.project, (response, error) => {
            if (!error) {
                this.setState({ message: "Project Saved Successfully!" });
                setTimeout(() => this.toProjects(), 500);
            } else this.setState({ error: error })
        })
        this.resetProject()
    }

    updateProject = (event) => {
        event.preventDefault()
        ProjectService.update(this.state.project, (response, error) => {
            if (!error) {
                this.setState({ message: "Project Updated Successfully!" });
                setTimeout(() => this.toProjects(), 500);
            } else this.setState({ error: error })
        })
        this.resetProject()
    }

    resetProject = () => this.setState(prevState => ({
        project: {
            ...Object.keys(prevState.project).map(attribute => attribute = ''),
            id: prevState.project.id
        }
    }))

    projectChange = (event) => {
        const { name, value } = event.target
        this.setState(prevState => ({
            project: {
                ...prevState.project,
                [name]: value
            }
        }));
    }

    numberChange = (event) => {
        const numberRegex = /^[0-9\b]*$/;
        const { name, value } = event.target
        if (numberRegex.test(value)) {
            this.setState(prevState => ({
                project: {
                    ...prevState.project,
                    [name]: value
                }
            }));
        }
    }

    toProjects = () => this.props.history.goBack()

    render() {
        return (
            <Project data={{
                ...this.state,
                updateProject: this.updateProject,
                saveProject: this.saveProject,
                resetProject: this.resetProject,
                projectChange: this.projectChange,
                numberChange: this.numberChange,
                toProjects: this.toProjects
            }} />
        )
    }
}

export default ProjectContainer;