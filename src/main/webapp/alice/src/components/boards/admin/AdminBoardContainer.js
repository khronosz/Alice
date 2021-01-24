import React, { Component } from "react";

import TempService from "../../../services/TempService";
import { AdminBoard } from "./AdminBoard";

export default class AdminBoardContainer extends Component {

    state = {
        content: ""
    }

    componentDidMount() {
        TempService.getAdminBoard().then(
            response => {
                this.setState({
                    content: response.data
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });
            }
        );
    }

    render() {
        return (
            <AdminBoard data={this.state} />
        );
    }
}