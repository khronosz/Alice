import React from "react";
import {Alert, Button, Card, Col, Form} from "react-bootstrap";
import {faGavel, faUndo} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import BootstrapTable from 'react-bootstrap-table-next';
import cellEditFactory, {Type} from 'react-bootstrap-table2-editor';
import MyToast from "../MyToast";

export default function UserDemands(props) {

    const {user, demands, goBack, onTableChange, error, message, messageType, resetMessage} = props.data

    const columns = [{
        dataField: 'projectName',
        text: 'Project Name',
        editable: false
    }, {
        dataField: 'utilization',
        text: 'Utilization',
        type: 'number',
        style: {
            width: '120px'
        },
        editor: {
            type: Type.TEXT,
            style: {
                height: '25px'
            }
        },
        validator: (newValue, row, column) => {
            if (isNaN(newValue)) {
                return {
                    valid: false,
                    message: 'Utilization should be numeric'
                };
            }
            if (newValue < 0 || newValue > 100) {
                return {
                    valid: false,
                    message: 'Utilization should be between 0 and 100'
                };
            }
            return true;
        }
    }, {
        dataField: 'projectStart',
        text: 'Project Join',
        style: {
          width: '170px'
        },
        formatter: (cell) => {
            let dateObj = cell;
            if (typeof cell !== 'object') {
                dateObj = new Date(cell);
            }
            return `${dateObj.getUTCFullYear()}-${('0' + (dateObj.getUTCMonth() + 1)).slice(-2)}-${('0' + dateObj.getUTCDate()).slice(-2)}`;
        },
        editor: {
            type: Type.DATE,
            style: {
                width: '170px'
            }
        }
    }, {
        dataField: 'projectEnd',
        text: 'Project Leave',
        style: {
            width: '170px',
            height: '30px'
        },
        formatter: (cell) => {
            let dateObj = cell;
            if (typeof cell !== 'object') {
                dateObj = new Date(cell);
            }
            return `${dateObj.getUTCFullYear()}-${('0' + (dateObj.getUTCMonth() + 1)).slice(-2)}-${('0' + dateObj.getUTCDate()).slice(-2)}`;
        },
        editor: {
            type: Type.DATE,
            style: {
                width: '170px',
                height: '30px'
            }
        }
    }];

    return (
        <div>
            <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()}/>
            <Card className="bg-dark text-white pt-3" style={{width: "40%"}}>
                <Card.Header><FontAwesomeIcon icon={faGavel}/>{" " + user.username + " Demands"}</Card.Header>
                <Card.Body>
                    {demands.length !== 0 ?
                        <div>
                            <Alert variant={'info'}>Double click on the cell to modify data</Alert>
                            <BootstrapTable keyField={'projectName'}
                                            data={demands}
                                            columns={columns}
                                            cellEdit={cellEditFactory({
                                                mode: 'dbclick',
                                                blurToSave: true
                                            })}
                                            remote={{cellEdit: true}}
                                            onTableChange={onTableChange}/>
                        </div>
                        :
                        <Alert variant={'info'}>No Projects Available</Alert>
                    }
                    {error && (
                        <Form.Row>
                            <Form.Group as={Col} controlId="formGridMessage">
                                <div className="alert alert-danger" role="alert">{error}</div>
                            </Form.Group>
                        </Form.Row>
                    )}

                </Card.Body>
                <Card.Footer style={{"textAlign": "right"}}>
                    <Button size="sm" variant="info" type="button" onClick={goBack}>
                        <FontAwesomeIcon icon={faUndo}/> Back
                    </Button>
                </Card.Footer>
            </Card>
        </div>
    )
}