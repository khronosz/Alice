import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "./App.css";

import NavigationBar from "./NavigationBar";
import LoginContainer from "./login/LoginContainer";
import HomeContainer from "./home/HomeContainer";
import ProfileContainer from "./profile/ProfileContainer";
import UserContainer from "./users/UserContainer";
import ModeratorBoardContainer from "./boards/moderator/ModeratorBoardContainer";
import AdminBoardContainer from "./boards/admin/AdminBoardContainer";
import TeamContainer from "./users/TeamContainer";
import Footer from "./Footer";
import ProjectListContainer from "./projects/ProjectListContainer";
import ProjectContainer from "./projects/ProjectContainer";
import DemandListContainer from "./demands/DemandListContainer";
import DemandContainer from "./demands/DemandContainer";
import UtilPlanContainer from "./utilplan/UtilPlanContainer";
import ChangePasswordContainer from "./password/ChangePasswordContainer";
import UserDemandsContainer from "./demands/UserDemandsContainer";
import DashboardContainer from "./boards/user/DashboardContainer";

export const API_URL = "http://localhost:8080"

class App extends Component {

    render() {

        return (
            <Router>
                <NavigationBar />
                <div className="container mt-3" style={{ width: "100%", maxWidth: "100%" }}>
                    <Switch>
                        <Route exact path={"/"} component={HomeContainer} />
                        <Route exact path="/login" component={LoginContainer} />
                        <Route exact path="/profile" component={ProfileContainer} />
                        <Route exact path="/team" component={TeamContainer} />
                        <Route exact path="/user" component={UserContainer} />
                        <Route exact path="/user/:id" component={UserContainer} />
                        <Route exact path="/projects" component={ProjectListContainer} />
                        <Route exact path="/project" component={ProjectContainer} />
                        <Route exact path="/project/:id" component={ProjectContainer} />
                        <Route exact path="/project/:id/demand" component={DemandContainer} />
                        <Route exact path="/project/:id/demand/:did" component={DemandContainer} />
                        <Route exact path="/project/:id/demands" component={DemandListContainer} />
                        <Route exact path="/moderator" component={ModeratorBoardContainer} />
                        <Route exact path="/admin" component={AdminBoardContainer} />
                        <Route exact path="/utilization" component={UtilPlanContainer} />
                        <Route exact path="/changePassword" component={ChangePasswordContainer} />
                        <Route exact path="/user/:id/demands" component={UserDemandsContainer} />
                        <Route exact path="/dashboard" component={DashboardContainer} />
                    </Switch>
                </div>
                <Footer />
            </Router>
        );
    }
}

export default App;
