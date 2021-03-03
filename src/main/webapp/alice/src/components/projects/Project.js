import MyToast from "../MyToast";
import {Button, Card, Col, Form, Jumbotron} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faBan, faEdit, faFileContract, faSave, faUndo} from "@fortawesome/free-solid-svg-icons";
import { OrderType, Phase, ProjectType, Status } from "../../enums/Enums";
import React from "react";
import { DatePicker, DropDownMenu, TextField } from "../tools/InputElements";

export default function Project(props) {

    const { currentUser, id, updateProject, saveProject, resetProject, projectChange, numberChange, goBack, message, error } = props.data

    const {
        projectName = "", sap = "", phase = "", status = "", manager = "", backupManager = "", owner = "", customer = "", bu = "", buHu = "",
        contactPerson = "", fte = "", start = "", end = "", orderType = "", projectType = "", description = "", comment = ""
    } = props.data.project;

    return (
        <div>
            <MyToast message={message} type={"success"} />
            {currentUser ?
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header>
                        <FontAwesomeIcon icon={id ? faEdit : faFileContract} />
                        {id ? " Update Project" : " Add Project"}
                    </Card.Header>
                    <Form id="projectFormId" onSubmit={id ? updateProject : saveProject} onReset={resetProject}>
                        <Card.Body>
                            <Form.Row>
                                <TextField name="projectName" variable={projectName} change={projectChange} />
                                <TextField name="sap" variable={sap} change={projectChange} />
                                <DropDownMenu name="phase" variable={phase} change={projectChange} options={Phase} />
                                <DropDownMenu name="status" variable={status} change={projectChange} options={Status} />
                            </Form.Row>
                            <Form.Row>
                                <TextField name="manager" variable={manager} change={projectChange} />
                                <TextField name="backupManager" variable={backupManager} change={projectChange} />
                                <TextField name="owner" variable={owner} change={projectChange} />
                                <TextField name="customer" variable={customer} change={projectChange} />
                            </Form.Row>
                            <Form.Row>
                                <TextField name="bu" variable={bu} change={projectChange} />
                                <TextField name="buHu" variable={buHu} change={projectChange} />
                                <TextField name="contactPerson" variable={contactPerson} change={projectChange} />
                                <TextField name="fte" variable={fte} change={numberChange} />
                            </Form.Row>
                            <Form.Row>
                                <DatePicker name="start" variable={start} change={projectChange} />
                                <DatePicker name="end" variable={end} change={projectChange} />
                                <DropDownMenu name="orderType" variable={orderType} change={projectChange} options={OrderType} />
                                <DropDownMenu name="projectType" variable={projectType} change={projectChange} options={ProjectType} />
                            </Form.Row>
                            <Form.Row>
                                <TextField name="description" variable={description} change={projectChange} />
                                <TextField name="comment" variable={comment} change={projectChange} />
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
                                <FontAwesomeIcon icon={faSave} /> {id ? "Update" : "Save"}
                            </Button>
                            <Button style={{ marginRight: 10 }} size="sm" variant="danger" type="reset">
                                <FontAwesomeIcon icon={faBan} /> Reset
							</Button>
                            <Button size="sm" variant="info" type="button" onClick={goBack}>
                                <FontAwesomeIcon icon={faUndo} /> Back
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