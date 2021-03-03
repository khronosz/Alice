import React from 'react';
import {Card} from "react-bootstrap";
import * as IoIcons from "react-icons/io";

function Reports() {
  return (
    <Card className={"border border-dark bg-dark text-white"} style={{width: "80%", marginTop: "0px"}}>
      <Card.Header>
        <IoIcons.IoIosPaper />{' '}Reports
      </Card.Header>
    </Card>
);
}

export default Reports;
