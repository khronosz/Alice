import { Button, Card, Col, Form } from "react-bootstrap";
import React from "react";
import { TextField } from "../tools/InputElements";

export function Login(props) {

    const { username, password, login, userChange, message } = props.data;

    return (
        <div>
            <Card className={"border border-dark bg-dark text-white"} style={{ width: '18rem' }}>
                <Form id="loginFormId" onSubmit={login}>
                    <Card.Body>
                        <Form.Row>
                            <TextField name={"username"} variable={username} change={userChange} />
                        </Form.Row>
                        <Form.Row>
                            <TextField name={"password"} variable={password} type="password" change={userChange} />
                        </Form.Row>
                        {message && (
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridMessage">
                                    <div className="alert alert-danger" role="alert">{message}</div>
                                </Form.Group>
                            </Form.Row>
                        )}
                        <Button size="sm" variant="info" type="submit" style={{ width: '12rem', marginTop: '30px' }}>Login</Button>
                    </Card.Body>
                </Form>
            </Card>
        </div>
    )
}