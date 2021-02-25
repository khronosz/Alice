import MyToast from "../MyToast";
import {Button, Card, Col, Form, Jumbotron} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faBan, faEdit, faList, faSave, faUndo, faUser} from "@fortawesome/free-solid-svg-icons";
import { Level } from "../../enums/Enums";
import React from "react";
import { DropDownMenu, FilterList, TextField } from "../tools/InputElements";
import 'react-bootstrap-typeahead/css/Typeahead.css';

export default function User(props) {

    const { message, error, id, currentUser, updateUser, saveUser, resetUser, userChange, toTeam, directManagerNames, directManagerChange, toDemands } = props.data;
    const { username = "", job = "", department = "", email = "", city = "", level = "", directManagerName, notes = "" } = props.data.user

    return (
        <div className={"container"} style={{ width: "50%", maxWidth: "50%" }}>
            <MyToast message={message} type={"success"} />
            {currentUser ?
                <Card className={"border border-dark bg-dark text-white"}>
                    <Card.Header>
                        <FontAwesomeIcon icon={id ? faEdit : faUser} />{' '}{id ? "Update " + username : "Add User"}
                    </Card.Header>
                    <Form id="userFormId" onSubmit={id ? updateUser : saveUser} onReset={resetUser}>
                        <Card.Body>
                            <Form.Row>
                                <TextField name="username" variable={username} change={userChange} />
                                <TextField name="job" variable={job} change={userChange} />
                            </Form.Row>
                            <Form.Row>
                                <TextField name="department" variable={department} change={userChange} />
                                <TextField name="email" type="email" variable={email} change={userChange} />
                            </Form.Row>
                            <Form.Row>
                                <TextField name="city" variable={city} change={userChange} />
                                <DropDownMenu name="level" variable={level} change={userChange} options={Level} />
                            </Form.Row>
                            <Form.Row>
                                <FilterList name="directManagerName" options={directManagerNames} change={directManagerChange} selected={directManagerName} />
                                <TextField name="notes" variable={notes} change={userChange} />
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
                            <Button style={{ marginRight: 10, display: id ? "inline-block" : "none" }} size="sm" variant="warning" type="submit" onClick={toDemands}>
                                <FontAwesomeIcon icon={faList} /> Demands
                            </Button>
                            <Button style={{ marginRight: 10 }} size="sm" variant="danger" type="reset" onClick={resetUser}>
                                <FontAwesomeIcon icon={faBan} /> Reset
							              </Button>
                            <Button size="sm" variant="info" type="button" onClick={toTeam}>
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
