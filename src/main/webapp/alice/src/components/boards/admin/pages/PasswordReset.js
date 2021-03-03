import React from 'react';
import {Card} from "react-bootstrap";
import * as AiIcons from "react-icons/ai";

function PasswordReset() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <AiIcons.AiFillSecurityScan />{' '}Password Reset
      </Card.Header>
    </Card>
  );
}

export default PasswordReset;