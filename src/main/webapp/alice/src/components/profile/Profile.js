import {Col, Container, Jumbotron, Row, Form, FormGroup, Button} from "react-bootstrap";
import React from "react";
import Card from "react-bootstrap/Card";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faKey, faSave, faUser} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";
import MyToast from "../MyToast";

export function Profile(props) {

    const {currentUser, user, message, messageType, resetMessage, saveUser, resetUser, userChange} = props.data;

    return (
        <div>
            <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()}/>
            {currentUser ?
                <Card className="bg-dark text-white pt-3">
                    <Card.Header><FontAwesomeIcon
                        icon={faUser}/>{' '}{currentUser.username.charAt(0).toUpperCase() + currentUser.username.slice(1)}'s
                        Profile</Card.Header>
                    <Card.Body>
                        <Container fluid>
                            <Row>
                                <Col md={3}>
                                    <strong>Username</strong>
                                    <p>{currentUser.username}</p>
                                    <strong>Roles</strong>
                                    <p>{currentUser.roles}</p>
                                    <strong>Last Login</strong>
                                    <p>{currentUser.lastLogin === null ? "You haven't logged in yet" : currentUser.lastLogin}</p>
                                    <Link to={"/changePassword"} className={"ml-auto btn btn-sm btn-info"} style={{marginTop: "96px"}}>
                                        <FontAwesomeIcon icon={faKey}/> Change Password
                                    </Link>
                                </Col>
                                <Col md={3}>

                                </Col>
                                <Col md={6}>
                                    <Form id="userFormId" onSubmit={saveUser} onReset={resetUser}>
                                        <Form.Row>
                                            <FormGroup as={Col} controlId="formUserName">
                                                <Form.Label>
                                                    Name
                                                </Form.Label>
                                                <Form.Control disabled type="text" name="username" defaultValue={user.username} onChange={userChange}/>
                                            </FormGroup>
                                            <FormGroup as={Col} controlId={"formUserJob"}>
                                                <Form.Label>
                                                    Job
                                                </Form.Label>
                                                <Form.Control type="text" name="job" defaultValue={user.job} onChange={userChange}/>
                                            </FormGroup>
                                        </Form.Row>
                                        <Form.Row>
                                            <FormGroup as={Col} controlId="formUserDepartment">
                                                <Form.Label>
                                                    City
                                                </Form.Label>
                                                <Form.Control type="text" name="city" defaultValue={user.city} onChange={userChange}/>
                                            </FormGroup>
                                            <FormGroup as={Col} controlId="formUserEmail">
                                                <Form.Label>
                                                    Email
                                                </Form.Label>
                                                <Form.Control type="email" name="email" defaultValue={user.email} onChange={userChange}/>
                                            </FormGroup>
                                        </Form.Row>
                                        <Form.Row>
                                            <FormGroup as={Col} controlId="formUserCity">
                                                <Form.Label>
                                                    Department
                                                </Form.Label>
                                                <Form.Control disabled defaultValue={user.department}/>
                                            </FormGroup>
                                            <FormGroup as={Col} controlId="formUserLevel">
                                                <Form.Label>
                                                    Level
                                                </Form.Label>
                                                <Form.Control disabled defaultValue={user.level}/>
                                            </FormGroup>
                                        </Form.Row>
                                        <Button variant={'success'} size="sm" type="submit">
                                            <FontAwesomeIcon icon={faSave} /> Save
                                        </Button>
                                        <Button style={{ float: "right" }} size="sm" variant="danger" type="reset">
                                            <FontAwesomeIcon icon={faBan} /> Cancel
                                        </Button>
                                    </Form>
                                </Col>
                            </Row>
                        </Container>
                    </Card.Body>
                </Card>
                :
                <Jumbotron className="bg-dark text-white">
                    <h3>Access denied!</h3>
                </Jumbotron>
            }
        </div>
    )
}