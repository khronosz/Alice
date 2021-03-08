import React from 'react';
import {Card} from "react-bootstrap";
import * as AiIcons from "react-icons/ai";

function Projects() {
  return (
    <Card className={"border border-dark bg-dark text-white admin-card"}>
      <Card.Header>
        <AiIcons.AiFillProject />{' '}Projects
      </Card.Header>
    </Card>
  );
}

export default Projects;