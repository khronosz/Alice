import { Jumbotron } from "react-bootstrap";
import React from "react";

export function ModeratorBoard(props) {
    return (
        <div>
            <Jumbotron className="bg-dark text-white">
                <h3>{props.data.content}</h3>
            </Jumbotron>
        </div>
    )
}