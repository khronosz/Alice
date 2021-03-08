import React from 'react';
import {Card} from "react-bootstrap";
import * as GiIcons from "react-icons/gi";

function Demands() {
  return (
    <Card className={"border border-dark bg-dark text-white admin-card"}>
      <Card.Header>
        <GiIcons.GiGavel />{' '}Demands
      </Card.Header>
    </Card>
  );
}

export default Demands;