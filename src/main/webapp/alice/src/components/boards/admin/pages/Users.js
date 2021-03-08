import React from 'react';
import {Card} from "react-bootstrap";
import * as IoIcons from "react-icons/io";

function Users() {
  return (
    <Card className={"border border-dark bg-dark text-white admin-card"}>
      <Card.Header>
        <IoIcons.IoIosPaper/>{' '}Users
      </Card.Header>
    </Card>
  );
}

export default Users;