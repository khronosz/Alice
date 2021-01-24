import React from 'react';
import { ProjectList } from "./ProjectList";
import ProjectService from "../../services/ProjectService";
import ExportService from '../../services/ExportService';

class ProjectListContainer extends React.Component {

    state = {
        content: "",
        projects: [],
        search: '',
        currentPage: 1,
        projectsPerPage: 5,
        message: undefined
    }

    componentDidMount() {
        this.findAllProject()
    }

    findAllProject = () => ProjectService.findAll((response, error) => {
        if (!error) this.setState({ projects: response })
        else this.setState({ content: error.message })
    })

    deleteProject = (projectId) => ProjectService.delete(projectId, (response, error) => {
        if (!error) {
            this.setState({ message: "Project Deleted Successfully!" })
            this.setState({ projects: this.state.projects.filter(project => project.id !== projectId) });
        }
    })

    updateProject = (id) => this.props.history.push("/project/" + id);

    toDemands = (id) => this.props.history.push("/project/" + id + "/demands")

    resetMessage = () => this.setState({ message: undefined })

    getExport = () => ExportService.download("/projects/export", "projects")

    setPage = number => this.setState({currentPage: number})
    
    searchChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    cancelSearch = () => {
        this.setState({ search: '' });
        this.findAllProject();
    }

    searchData = () => ProjectService.findAllBySearchText(this.state.search, (response, error) => {
        if (!error) {
            this.setState({ projects: response, currentPage: 1 })
        }
    })

    searchKeyDown = event => {
        if (event.key === 'Enter') {
            this.searchData()
        }
    }

    render() {
        return (
            <ProjectList data={{
                ...this.state,
                deleteProject: this.deleteProject,
                updateProject: this.updateProject,
                getExport: this.getExport,
                resetMessage: this.resetMessage,
                toDemands: this.toDemands,
                setPage: this.setPage,
                searchChange: this.searchChange,
                cancelSearch: this.cancelSearch,
                searchData: this.searchData,
                searchKeyDown: this.searchKeyDown
            }} />
        );
    }
}

export default ProjectListContainer;