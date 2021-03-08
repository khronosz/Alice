import React from 'react';
import {Card} from "react-bootstrap";
import * as AiIcons from "react-icons/ai";

function PasswordReset() {
  return (
    <Card className={"border border-dark bg-dark text-white admin-card"}>
      <Card.Header>
        <AiIcons.AiFillSecurityScan />{' '}Password Reset
      </Card.Header>
    </Card>
  );
}

export default PasswordReset;