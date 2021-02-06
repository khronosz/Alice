import React from 'react';
import UtilPlan from "./UtilPlan";
import UserService from "../../services/UserService";
import ExportService from '../../services/ExportService';

class UtilPlanContainer extends React.Component {

    state = {
        error: "",
        users: [],
        currentPage: 1,
        usersPerPage: 10,
        message: undefined
    }

    componentDidMount() {
        this.findAllUsers();
    }

    findAllUsers = () => UserService.findUtilPlanUsers((response, error) => {
        if (response) this.setState({ users: response })
        else this.setState({ error: error && error.message })
    })

    deleteUser = userId => {
        UserService.delete(userId, (response, error) => {
            if (!error) {
                this.setState({ message: "User Deleted Successfully!" });
                this.setState({ users: this.state.users.filter(user => user.id !== userId) });
            }
        })
    }

    editUser = id => this.props.history.push("/user/" + id);

    getExport = () => ExportService.download("/utilization/export", "utilization_plan")

    resetMessage = () => this.setState({ message: undefined })

    setPage = number => this.setState({currentPage: number})

    render() {
        return (
            <UtilPlan data={{
                ...this.state,
                editUser: this.editUser,
                deleteUser: this.deleteUser,
                getExport: this.getExport,
                resetMessage: this.resetMessage,
                setPage: this.setPage
            }} />
        );
    }
}

export default UtilPlanContainer;