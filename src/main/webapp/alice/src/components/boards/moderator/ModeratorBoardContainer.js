import React, { Component } from "react";

import TempService from "../../../services/TempService";
import { ModeratorBoard } from "./ModeratorBoard";

export default class ModeratorBoardContainer extends Component {

    state = {
        content: ""
    }

    componentDidMount() {
        TempService.getModeratorBoard().then(
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
            <ModeratorBoard data={this.state} />
        );
    }
}