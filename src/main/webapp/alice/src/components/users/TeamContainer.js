import React from 'react';
import Team from "./Team";
import UserService from "../../services/UserService";
import ExportService from '../../services/ExportService';

class TeamContainer extends React.Component {

    state = {
        error: "",
        users: [],
        currentPage: 1,
        usersPerPage: 10,
        messageType: undefined,
        message: undefined
    }

    componentDidMount() {
        this.findAllUsers();
    }

    findAllUsers = () => UserService.findAll((response, error) => {
        if (!error) this.setState({ users: response })
        else this.setState({ error: error.message })
    })

    chkLastValidation = userId => {
        UserService.chkLastValidation(userId, (response, error) => {
            if (!error) {
                this.setState({ message: "User Validated Successfully!", messageType: "success" });
            }
            window.location.reload();
        })
    }

    deleteUser = userId => {
        UserService.delete(userId, (response, error) => {
            if (!error) {
                this.setState({ message: "User Deleted Successfully!", messageType: "danger" });
                this.setState({ users: this.state.users.filter(user => user.id !== userId) });
            }
        })
    }

    editUser = id => this.props.history.push("/user/" + id);

    getExport = () => ExportService.download("/team/export", "team")

    resetMessage = () => this.setState({ message: undefined })

    setPage = number => this.setState({currentPage: number})

    render() {
        return (
            <Team data={{
                ...this.state,
                chkLastValidation: this.chkLastValidation,
                editUser: this.editUser,
                deleteUser: this.deleteUser,
                getExport: this.getExport,
                resetMessage: this.resetMessage,
                setPage: this.setPage
            }} />
        );
    }
}

export default TeamContainer;