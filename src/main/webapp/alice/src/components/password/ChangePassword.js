import {Card, Form, OverlayTrigger, Button, Col, Tooltip} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React from "react";
import {faBan, faCheck, faKey} from "@fortawesome/free-solid-svg-icons";
import MyToast from "../MyToast";

export function ChangePassword(props) {

    const {currentUser, passwordDto, error, message, messageType, changePassword, inputChange, toProfile} = props.data;

    return (
        <div>
            <MyToast message={message} type={messageType}/>
            <Card className={"border border-dark bg-dark text-white"} style={{ width: '25rem' }}>
                <Card.Header>
                    <FontAwesomeIcon icon={faKey}/>{' '}Change Password for {currentUser.username.charAt(0).toUpperCase() + currentUser.username.slice(1)}
                </Card.Header>
                <Form id="changePwdFormId" onSubmit={changePassword}>
                    <Card.Body>
                        <Form.Group controlId="formGroupCurrentPwd">
                            <Form.Label>Current password</Form.Label>
                            <Form.Control name={"currentPassword"} variable={passwordDto.currentPassword} type="password"  placeholder="current password" onChange={inputChange}/>
                        </Form.Group>
                        <Form.Group controlId="formGroupNewPwd">
                            <Form.Label>New password</Form.Label>
                                <OverlayTrigger key={'right'} placement={'right'} delay={500}
                                    overlay={
                                        <Tooltip id={'pwdTooltip'} style={{fontStyle: "italic"}}>
                                            <strong>The password must:</strong>
                                            <ul>
                                                <li>be at least <strong>8</strong> characters in length</li>
                                                <li>contain both <strong>upper</strong> and <strong>lower</strong> case characters</li>
                                                <li>contain at least 1 <strong>number</strong></li>
                                                <li>contain at least one of these <strong>special characters</strong>: _.@$!%*?&-</li>
                                            </ul>
                                        </Tooltip>
                                    }
                                >
                                    <Form.Control name={"newPassword"} variable={passwordDto.newPassword} type="password" placeholder="new password" onChange={inputChange}/>
                                </OverlayTrigger>
                        </Form.Group>
                        <Form.Group controlId="formGroupConfirmPwd">
                            <Form.Label>Confirm password</Form.Label>
                            <Form.Control name={"confirmPassword"} variable={passwordDto.confirmPassword} type="password" placeholder="confirm password" onChange={inputChange}/>
                        </Form.Group>
                        {error && (
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridMessage">
                                    <div className="alert alert-danger" role="alert">{error}</div>
                                </Form.Group>
                            </Form.Row>
                        )}
                    </Card.Body>
                    <Card.Footer style={{"textAlign": "right"}}>
                        <Button style={{marginRight: 10}} size="sm" variant="success" type="submit">
                            <FontAwesomeIcon icon={faCheck}/> Change
                        </Button>
                        <Button size="sm" variant="danger" type="button" onClick={toProfile}>
                            <FontAwesomeIcon icon={faBan}/> Cancel
                        </Button>
                    </Card.Footer>
                </Form>
            </Card>
        </div>
    )
}