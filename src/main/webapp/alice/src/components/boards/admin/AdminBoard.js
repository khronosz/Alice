import React from "react";
import {Jumbotron} from "react-bootstrap";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Sidebar from "./menu/Sidebar";
import Reports from "./pages/Reports";
import Users from "./pages/Users";
import Projects from "./pages/Projects";
import PasswordReset from "./pages/PasswordReset";
import Groups from "./pages/Groups";
import DemandsContainer from "./pages/DemandsContainer";

export default function AdminBoard(props) {

  const error = props.data;

  return (
    <>
      {error ?
        <Jumbotron className="bg-dark text-white">
          <h3>{error}</h3>
        </Jumbotron>
        :
        <Router>
          <Sidebar />
          <Switch>
            <Route path='/admin/users' component={Users} />
            <Route path='/admin/projects' component={Projects} />
            <Route path='/admin/demands' component={DemandsContainer} />
            <Route path='/admin/reports' component={Reports} />
            <Route path='/admin/passwordReset' component={PasswordReset} />
            <Route path='/admin/groups' component={Groups} />
          </Switch>
        </Router>
      }
    </>
  )
}