import React from 'react';
import DemandList from "./DemandList";
import ProjectService from "../../services/ProjectService";
import DemandService from "../../services/DemandService";
import ExportService from '../../services/ExportService';

export default class DemandListContainer extends React.Component {

    state = {
        content: "",
        projectId: this.props.match.params.id,
        demands: [],
        project: {},
        projectIds: [],
        idx: 0,
        currentPage: 1,
        demandsPerPage: 10,
        message: undefined
    }

    componentDidMount() {
        this.findAllId();
        this.findAllDemands();
        this.findParentProject(this.state.projectId)
    }

    initIndex(id) {
        let i;
        for (i = 0; i < this.state.projectIds.length; i++) {
            if (parseInt(this.state.projectIds[i]) === parseInt(id)) {
               this.setState({ idx: i })
            }
        }
    }

    nextBtn = () => {
        this.findParentProject(this.state.projectIds[this.state.idx +1])
        DemandService.findAll(this.state.projectIds[this.state.idx + 1], (response, error) => {
            if (response) this.setState({ demands: response })
            else this.toProjects()
        })
        this.initIndex(this.state.projectIds[this.state.idx + 1])
    }

    prevBtn = () => {
        this.findParentProject(this.state.projectIds[this.state.idx -1])
        DemandService.findAll(this.state.projectIds[this.state.idx - 1], (response, error) => {
            if (response) this.setState({ demands: response })
            else this.toProjects()
        })
        this.initIndex(this.state.projectIds[this.state.idx - 1])
    }

    findAllId = () => ProjectService.findAllId((response, error) => {
        if (response) {
            this.setState({ projectIds: response})
            this.initIndex(this.state.projectId)
        }
        else this.toProjects()
    })


    findParentProject = (id) => ProjectService.findById(id, (response, error) => {
        if (response) this.setState({ project: response })
        else this.toProjects()
    })

    findAllDemands = () => DemandService.findAll(this.state.projectId, (response, error) => {
        if (response) this.setState({ demands: response })
        else this.toProjects()
    })

    deleteDemand = demandId => DemandService.delete(this.state.projectId, demandId, (response, error) => {
        if (!error) {
            this.setState({ message: "Demand Deleted Successfully!" });
            this.setState({ demands: this.state.demands.filter(demand => demand.id !== demandId) });
        }
    })

    editDemand = id => this.props.history.push("/project/" + this.state.projectIds[this.state.idx] + "/demand/" + id)

    toProjects = () => this.props.history.push("/projects")

    getExport = () => ExportService.download("/project/" + this.state.projectIds[this.state.idx] + "/demands/export", "demands")

    resetMessage = () => this.setState({ message: undefined })

    setPage = number => this.setState({currentPage: number})

    render() {
        return (
            <DemandList data={{
                ...this.state,
                editDemand: this.editDemand,
                deleteDemand: this.deleteDemand,
                getExport: this.getExport,
                toProjects: this.toProjects,
                resetMessage: this.resetMessage,
                setPage: this.setPage,
                initIndex: this.initIndex,
                nextBtn: this.nextBtn,
                prevBtn: this.prevBtn
            }} />
        );
    }
}