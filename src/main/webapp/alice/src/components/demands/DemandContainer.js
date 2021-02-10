import React from "react"
import AuthService from "../../services/AuthService";
import Demand from "./Demand";
import ProjectService from "../../services/ProjectService";
import DemandService from "../../services/DemandService";
import UserService from "../../services/UserService";

export default class DemandContainer extends React.Component {

    state = {
        showMessage: false,
        currentUser: AuthService.getCurrentUser(),
        projectId: this.props.match.params.id,
        demandId: this.props.match.params.did,
        project: {},
        demand: {},
        team: [],
        message: undefined,
        error: undefined
    }

    componentDidMount() {
        this.findProject()
        if (this.state.demandId) this.findDemand()
        this.findAllUsers()
    }

    findProject = () => ProjectService.findById(this.state.projectId, (response, error) => {
        if (!error) this.setState({ project: response });
        else {
            this.setState({ error: error && error.message })
            this.toDemands()
        }
    })

    findDemand = () => DemandService.findById(this.state.projectId, this.state.demandId, (response, error) => {
        if (!error) this.setState({ demand: response });
        else {
            this.setState({ error: error && error.message })
            this.toDemands()
        }
    })

    findAllUsers = () => UserService.findAllUsers((response, error) => {
        if (response) this.setState({ team: response })
        else this.setState({ error: error && error.message })
    })

    saveDemand = event => {
        event.preventDefault();
        DemandService.save(this.state.projectId, this.state.demand, (response, error) => {
            if (!error) {
                this.setState({ message: "Demand Saved Successfully!" });
                this.resetDemand()
                setTimeout(() => this.toDemands(), 500);
            } else this.setState({ error: error })
        });
    }

    updateDemand = event => {
        event.preventDefault();
        DemandService.update(this.state.projectId, this.state.demand.id, this.state.demand, (response, error) => {
            if (!error) {
                this.setState({ message: "Demand Updated Successfully!" });
                this.resetDemand()
                setTimeout(() => this.toDemands(), 500);
            } else this.setState({ error: error })
        });
    }

    resetDemand = () => {
        this.setState(prevState => ({
            demand: {
                ...Object.keys(prevState.demand).map(attribute => attribute = ''),
                id: prevState.demand.id,
                project: prevState.demand.project
            }
        }));
    }

    demandChange = event => {
        const { name, value } = event.target
        this.setState(prevState => ({
            demand: {
                ...prevState.demand,
                [name]: value
            }
        }));
    }

    utilizationChange = (event) => {
        const numberRegex = /^[0-9\b]*$/;
        const { name, value } = event.target
        if (numberRegex.test(value) && value <= 100) {
            this.setState(prevState => ({
                demand: {
                    ...prevState.demand,
                    [name]: value
                }
            }));
        }
    }

    userChange = event => {
        const selected = event[0]
        this.setState(prevState => ({
            demand: {
                ...prevState.demand,
                username: selected
            }
        }))
    }

    toDemands = () => this.props.history.push("/project/" + this.props.match.params.id + "/demands");

    resetMessage = () => this.setState({ message: undefined })

    render() {
        return (
            <Demand data={{
                ...this.state,
                resetDemand: this.resetDemand,
                saveDemand: this.saveDemand,
                updateDemand: this.updateDemand,
                demandChange: this.demandChange,
                utilizationChange: this.utilizationChange,
                toDemands: this.toDemands,
                resetMessage: this.resetMessage,
                userChange: this.userChange
            }} />
        )
    }
}