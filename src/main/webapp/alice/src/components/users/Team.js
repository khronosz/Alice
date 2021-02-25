import { Button, ButtonGroup, Card, Jumbotron, Table } from "react-bootstrap";
import MyToast from "../MyToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faPlus, faTrash, faUsers, faCheck } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import { ExportButton } from "../tools/ExportButton";
import Pagination from "../fragments/Pagination";
import { ConfirmAlert } from "../fragments/ConfirmAlert";

export default function Team(props) {

    const {
        error, currentUser, users, currentPage, usersPerPage, getExport, chkLastValidation, editUser,
        deleteUser, messageType, message, resetMessage, setPage
    } = props.data

    const lastIndex = currentPage * usersPerPage;
    const firstIndex = lastIndex - usersPerPage;
    const currentUsers = users.slice(firstIndex, lastIndex);
    const totalPages = users.length !== 0 ? Math.ceil(users.length / usersPerPage) : 1;

    const [show, setShow] = useState(false);
    let deletedId = null;

    const openConfirmAlert = (userId) => {
        deletedId = userId;
        setShow(prev => !prev);
    };

    return (
        <div>
            <div style={{ "display": error ? "block" : "none" }}>
                <Jumbotron className="bg-dark text-white">
                    <h3>{error}</h3>
                </Jumbotron>
            </div>
            <div>
                <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()} />
                <div>
                    <ConfirmAlert show={show} setShow={setShow} target={deletedId} action={deleteUser(deletedId)}/>
                    <Card className={"border border-dark bg-dark text-white"}>
                        <Card.Header>
                            <FontAwesomeIcon icon={faUsers} />{' '}{currentUser.username.charAt(0).toUpperCase() + currentUser.username.slice(1)}'s Team
                            <ExportButton action={getExport} />
                            <Link to={"/user"} style={{ float: "right", marginRight: 10 }}
                                className={"ml-auto btn btn-sm btn-success"}>
                                <FontAwesomeIcon icon={faPlus} /> User
                            </Link>
                        </Card.Header>
                        <Card.Body>
                            <Table bordered hover striped variant="dark">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Job Title</th>
                                        <th>Department</th>
                                        <th>Email</th>
                                        <th>City</th>
                                        <th>Level</th>
                                        <th>Projects</th>
                                        <th>Utilization</th>
                                        <th>Project Leave</th>
                                        <th>Notes</th>
                                        <th>Last Validation</th>
                                        <th style={{ width: "1px" }}>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {currentUsers.length === 0 ?
                                        <tr align="center">
                                            <td colSpan="13">No Users Available</td>
                                        </tr> :
                                        currentUsers.map((user) => (
                                            <Row key={user.id} data={user} openConfirmAlert={openConfirmAlert} updateUser={editUser}
                                                chkLastValidation={chkLastValidation} />
                                        ))}
                                </tbody>
                            </Table>
                        </Card.Body>
                        <Card.Footer>
                            <Pagination data={{
                                currentPage: currentPage,
                                setPage: setPage,
                                totalPages: totalPages,
                                itemCount: users.length,
                                itemsPerPage: usersPerPage
                            }}
                            />
                        </Card.Footer>
                    </Card>
                </div>
            </div>
        </div>
    )
}

function Row(props) {

    const user = props.data

    return (
        <tr>
            <td>{user.username}</td>
            <td>{user.job}</td>
            <td>{user.department}</td>
            <td>{user.email}</td>
            <td>{user.city}</td>
            <td>{user.level}</td>
            <td>{user.projectNames}</td>
            <td>{user.utilization}</td>
            <td>{user.projectEnd}</td>
            <td>{user.notes}</td>
            <td>{user.lastValidation}</td>
            <td style={{ textAlign: "center" }}>
                <ButtonGroup>
                    <Button size="sm" variant="outline-primary" onClick={() => props.updateUser(user.id)}
                            style={{ marginRight: 10 }}>
                        <FontAwesomeIcon icon={faEdit} /></Button>
                    <Button size="sm" variant="outline-danger" onClick={() => props.openConfirmAlert(user.id)}>
                        <FontAwesomeIcon icon={faTrash} /></Button>
                    <Button size="sm" variant="outline-success" onClick={() => props.chkLastValidation(user.id)}
                            style={{ marginLeft: 10 }}>
                        <FontAwesomeIcon icon={faCheck} /></Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}