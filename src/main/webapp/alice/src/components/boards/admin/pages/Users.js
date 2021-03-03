import React from 'react';
import {Card} from "react-bootstrap";
import * as FaIcons from "react-icons/fa";

function Users() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <FaIcons.FaUsersCog />{' '}Users
      </Card.Header>
    </Card>
  );
}

export default Users;