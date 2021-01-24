import {Card, Jumbotron, Form, FormGroup, Col, Button} from "react-bootstrap";
import Spinner from 'react-bootstrap/Spinner'
import React from "react";
import MyToast from "../MyToast";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faChevronLeft, faChevronRight, faSave} from "@fortawesome/free-solid-svg-icons";

export function Home(props) {

    const {
        content, user, project, projects, message, messageType, resetMessage,
        loading, idx, nextBtn, prevBtn, updateProject, resetProject, projectChange, hasChanged
    } = props.data

    if (loading) {
        return (
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner>
        )
    }
    return (
        <div>
            <div style={{"display": content ? "block" : "none"}}>
                <Jumbotron className="bg-dark text-white">
                    <h3>{content}</h3>
                </Jumbotron>
            </div>
            <div>
                <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()}/>
                <div>
                    <Button size="sm" variant="" type="button" onClick={() => prevBtn()} className="chevron-button"
                            style={{
                                display: idx === 0 ? 'none' : 'inline-block',
                                position: "absolute",
                                top: "45%",
                                left: "7%"
                            }}>
                        <FontAwesomeIcon icon={faChevronLeft} size="3x" color="white"/>
                    </Button>
                    <Card className={"border border-dark bg-dark text-white"} style={{width: "60vw"}}>
                        <Form id="homeFormId" onSubmit={updateProject} onReset={resetProject}>
                            <Card.Header>
                                {project && project.projectName}
                            </Card.Header>
                            <Card.Body>
                                <Form.Row>
                                    <FormGroup as={Col} controlId={"formProjectDesc"}>
                                        <Form.Label>
                                            Customer
                                        </Form.Label>
                                        <Form.Control type="text" name="customer" value={project && project.customer}
                                                      onChange={projectChange} disabled={!user}
                                                      style={{
                                                          border: user ? "1px solid white" : "none",
                                                          resize: "none"
                                                      }}/>
                                        <Form.Label>
                                            Description
                                        </Form.Label>
                                        <Form.Control as="textarea" rows={10} name="description"
                                                      value={project && project.description !== null ? project.description : ""}
                                                      disabled={!user}
                                                      onChange={projectChange}
                                                      style={{
                                                          border: user ? "1px solid white" : "none",
                                                          resize: "none"
                                                      }}>
                                        </Form.Control>
                                        <Form.Label>
                                            Technology
                                        </Form.Label>
                                        <Form.Control as="textarea" rows={3} name="technology"
                                                      disabled={!user}
                                                      value={project && project.technology !== null ? project.technology : ""}
                                                      onChange={projectChange}
                                                      style={{
                                                          border: user ? "1px solid white" : "none",
                                                          resize: "none"
                                                      }}>
                                        </Form.Control>
                                    </FormGroup>
                                </Form.Row>
                            </Card.Body>
                            <Card.Footer style={{display: user ? 'block' : 'none', textAlign: "right"}}>
                                <Button size="sm" variant="success" style={{float: "left"}} type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Save
                                </Button>
                                <Button size="sm" variant="danger" type="reset" disabled={!hasChanged}>
                                    <FontAwesomeIcon icon={faBan}/> Cancel
                                </Button>
                            </Card.Footer>
                        </Form>
                    </Card>
                    <Button size="sm" variant="" type="button" onClick={() => nextBtn()} className="chevron-button"
                            style={{
                                display: idx === projects.length - 1 ? 'none' : 'inline-block',
                                position: "absolute",
                                top: "45%",
                                right: "7%"
                            }}>
                        <FontAwesomeIcon icon={faChevronRight} size="3x" color="white"/>
                    </Button>
                </div>
            </div>
        </div>
    )
}