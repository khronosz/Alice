import React from 'react';
import {Card} from "react-bootstrap";
import * as HiIcons from "react-icons/hi";

function Groups() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <HiIcons.HiUserGroup />{' '}Groups
      </Card.Header>
    </Card>
  );
}

export default Groups;