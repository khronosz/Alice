import React from 'react';
import {Card} from "react-bootstrap";
import * as IoIcons from "react-icons/io";

function Reports() {
  return (
    <Card className={"border border-dark bg-dark text-white admin-card"}>
      <Card.Header>
        <IoIcons.IoIosPaper/>{' '}Reports
      </Card.Header>
    </Card>
  );
}

export default Reports;
