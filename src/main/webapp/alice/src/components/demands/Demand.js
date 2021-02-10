import React from "react";
import MyToast from "../MyToast";
import {Button, Card, Col, Form, Jumbotron} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faBan, faEdit, faGavel, faList, faSave} from "@fortawesome/free-solid-svg-icons";
import { TextField, DatePicker, FilterList } from "../tools/InputElements";
import "../TypeAhead.css"

export default function Demand(props) {

    const { resetDemand, project, demand, demandChange, utilizationChange, saveDemand, updateDemand, currentUser, message, error, team, toDemands, resetMessage, userChange } = props.data

    return (
        <div className={"container"} style={{ width: "50%", maxWidth: "50%" }}>
            <MyToast message={message} type={"success"} reset={() => resetMessage()} />
            {currentUser ?
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header>
                        <FontAwesomeIcon icon={demand.id ? faEdit : faGavel} />
                        {demand.id ? " Update Demand of " : " Add Demand to "}{project.projectName}
                    </Card.Header>
                    <Form id="demandFormId" onSubmit={demand.id ? updateDemand : saveDemand} onReset={resetDemand}>
                        <Card.Body>
                            <Form.Row>
                                <TextField name="name" variable={demand.name} change={demandChange} />
                                <TextField name="mib" variable={demand.mib} change={demandChange} />
                            </Form.Row>
                            <Form.Row>
                                <FilterList name="user" options={team} field="username" change={userChange} selected={demand.username} />
                                <TextField name="utilization" variable={demand.utilization} change={utilizationChange} req={demand.username !== null && demand.username !== undefined} />
                            </Form.Row>
                            <Form.Row>
                                <DatePicker name="projectStart" variable={demand.projectStart || ""} change={demandChange} req={demand.username !== null && demand.username !== undefined} />
                                <DatePicker name="projectEnd" variable={demand.projectEnd || ""} change={demandChange} req={demand.username !== null && demand.username !== undefined} />
                            </Form.Row>
                            {error && (
                                <Form.Row>
                                    <Form.Group as={Col} controlId="formGridMessage">
                                        <div className="alert alert-danger" role="alert">{error}</div>
                                    </Form.Group>
                                </Form.Row>
                            )}
                        </Card.Body>
                        <Card.Footer style={{ "textAlign": "right" }}>
                            <Button style={{ float: "left" }} size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave} /> {demand.id ? "Update" : "Save"}
                            </Button>
                            <Button style={{ marginRight: 10 }} size="sm" variant="danger" type="reset">
                                <FontAwesomeIcon icon={faBan} /> Reset
							</Button>
                            <Button size="sm" variant="info" type="button" onClick={toDemands}>
                                <FontAwesomeIcon icon={faList} /> Demand List
							</Button>
                        </Card.Footer>
                    </Form>
                </Card>
                :
                <Jumbotron className="bg-dark text-white">
                    <h3>Access denied!</h3>
                </Jumbotron>
            }
        </div>
    )
}