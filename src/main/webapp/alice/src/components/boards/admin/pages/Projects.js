import React from 'react';
import {Card} from "react-bootstrap";
import * as AiIcons from "react-icons/ai";

function Projects() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <AiIcons.AiFillProject />{' '}Projects
      </Card.Header>
    </Card>
  );
}

export default Projects;