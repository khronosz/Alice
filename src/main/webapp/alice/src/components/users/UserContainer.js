import React from 'react';
import AuthService from "../../services/AuthService";
import User from "./User";
import UserService from "../../services/UserService";

class UserContainer extends React.Component {

    state = {
        id: this.props.match.params.id,
        currentUser: AuthService.getCurrentUser(),
        user: {},
        directManagerNames: [],
        message: undefined,
        error: undefined
    }

    componentDidMount() {
        this.findAllUsers()
        if (this.state.id) this.findUser(this.state.id);
        else this.setState({ directManager: this.findDirectManager(this.state.currentUser.id) })
    }

    findAllUsers = () => UserService.findAllDirectManagerNames((response, error) => {
        if (response) this.setState({ directManagerNames: response });
        else this.setState({ error: error.message })
    })

    findUser = (userId) => UserService.findById(userId, (response, error) => {
        if (!error) this.setState({ user: response })
        else this.setState({ error: error.message })
    })

    findDirectManager = (managerId) => UserService.findById(managerId, (response, error) => {
        if (!error) this.setState(prevState => ({
            user: {
                ...prevState.user,
                directManagerName: response
            }
        }))
        else this.setState({ error: error.message })
    })

    saveUser = event => {
        event.preventDefault();
        UserService.save(this.state.user, (response, error) => {
            if (!error) {
                this.setState({ message: "User Saved Successfully!" });
                this.resetUser()
                setTimeout(() => this.toTeam(), 500);
            } else this.setState({ error: error })
        })
    }

    updateUser = event => {
        event.preventDefault();
        UserService.update(this.state.user.id, this.state.user, (response, error) => {
            if (!error) {
                this.setState({ message: "User Updated Successfully!" });
                this.resetUser()
                setTimeout(() => this.toTeam(), 500);
            } else this.setState({ error: error })
        })
    }

    resetUser = () => this.setState(prevState => ({
        user: {
            ...Object.keys(prevState.user).map(attribute => attribute = ''),
            id: prevState.user.id
        }
    }));

    userChange = event => {
        const { name, value } = event.target
        this.setState(prevState => ({
            user: {
                ...prevState.user,
                [name]: value
            }
        }));
    }

    directManagerChange = event => {
        const selected = event[0]
        this.setState(prevState => ({
            user: {
                ...prevState.user,
                directManagerName: selected
            }
        }))
    }

    toTeam = () => this.props.history.goBack()

    toDemands = () => {
        this.props.history.push("/user/" + this.state.user.id + "/demands" )
    }

    render() {
        return (
            <User data={{
                ...this.state,
                updateUser: this.updateUser,
                saveUser: this.saveUser,
                resetUser: this.resetUser,
                userChange: this.userChange,
                toTeam: this.toTeam,
                directManagerChange: this.directManagerChange,
                toDemands: this.toDemands
            }} />
        )
    }
}

export default UserContainer;