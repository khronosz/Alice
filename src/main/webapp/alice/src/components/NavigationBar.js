import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import {withRouter} from "react-router-dom";
import AuthService from "../services/AuthService";

class NavigationBar extends React.Component {

    state = {
        currentUser: AuthService.getCurrentUser(),
        showModeratorBoard: false,
        showAdminBoard: false,
        showDashboard: false
    }

    componentDidMount() {
        const user = this.state.currentUser
        if (user) {
            this.setState({
                showModeratorBoard: user.roles.includes("ROLE_MODERATOR"),
                showAdminBoard: user.roles.includes("ROLE_ADMIN"),
                showDashboard: user.roles.includes("ROLE_USER")
            });
        } else if (window.location.pathname !== "/") {
            this.props.history.push("/login")
        }
    }

    logout = () => AuthService.logout()

    render() {
        const { currentUser, showModeratorBoard, showAdminBoard, showDashboard } = this.state;

        return (
            <Navbar bg="dark" variant="dark">
                <Nav className="mr-auto">
                    <Navbar.Brand href="/">Home</Navbar.Brand>

                    {showAdminBoard &&
                        <Nav.Link href="/admin"> Admin Board</Nav.Link>
                    }

                    {(showAdminBoard || showModeratorBoard) &&
                        <Nav.Link href="/moderator"> Moderator Board</Nav.Link>
                    }

                    {(showAdminBoard || showDashboard) &&
                        <Nav.Link href="/dashboard">Dashboard</Nav.Link>
                    }

                    {currentUser &&
                        <>
                            <Nav.Link href="/team">Team</Nav.Link>
                            <Nav.Link href="/projects">Projects</Nav.Link>
                            <Nav.Link href="/utilization">Utilization Plan</Nav.Link>
                        </>
                    }
                </Nav>
                <Nav className="ml-auto">
                    {currentUser ?
                        <React.Fragment key='currentUser'>
                            <Nav.Link href="/profile">{currentUser.username}</Nav.Link>
                            <Nav.Link href="/login" onClick={this.logout}>Logout</Nav.Link>
                        </React.Fragment>
                        :
                        <Nav.Link href="/login">Login</Nav.Link>
                    }
                </Nav>
            </Navbar>
        );
    }
}

export default withRouter(NavigationBar);