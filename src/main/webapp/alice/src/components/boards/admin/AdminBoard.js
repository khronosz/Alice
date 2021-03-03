import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Sidebar from "./menu/Sidebar";
import Reports from "./pages/Reports";
import Users from "./pages/Users";
import Projects from "./pages/Projects";
import Demands from "./pages/Demands";
import PasswordReset from "./pages/PasswordReset";
import Groups from "./pages/Groups";

export default function AdminBoard() {

  return (
    <>
      <Router>
        <Sidebar />
        <Switch>
          <Route path='/admin/users' component={Users} />
          <Route path='/admin/projects' component={Projects} />
          <Route path='/admin/demands' component={Demands} />
          <Route path='/admin/reports' component={Reports} />
          <Route path='/admin/passwordReset' component={PasswordReset} />
          <Route path='/admin/groups' component={Groups} />
        </Switch>
      </Router>
    </>
  )
}