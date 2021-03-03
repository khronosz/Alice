import React from 'react';
import {Card} from "react-bootstrap";
import * as GiIcons from "react-icons/gi";

function Demands() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <GiIcons.GiGavel />{' '}Demands
      </Card.Header>
    </Card>
  );
}

export default Demands;