import React from 'react';
import AllUsers from "./AllUsers";
import UserService from "../../../../services/UserService";
import AdminService from "../../../../services/AdminService";

class AllUsersContainer extends React.Component {

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

  findAllUsers = () => AdminService.findAllUsers((response, error) => {
    if (!error) this.setState({ users: response })
    else this.setState({ error: error.message })
  })

  editUser = id => this.props.history.push("/admin/manageUser/" + id);

  deleteUser = userId => {
    UserService.delete(userId, (response, error) => {
      if (!error) {
        this.setState({ message: "User Deleted Successfully!", messageType: "danger" });
        this.setState({ users: this.state.users.filter(user => user.id !== userId) });
      }
    })
  }

  resetMessage = () => this.setState({ message: undefined })

  setPage = number => this.setState({currentPage: number})

  render() {
    return (
      <AllUsers data={{
        ...this.state,
        editUser: this.editUser,
        deleteUser: this.deleteUser,
        resetMessage: this.resetMessage,
        setPage: this.setPage
      }} />
    )
  }
}

export default AllUsersContainer;