import {Button, ButtonGroup, Card, Jumbotron, Table} from "react-bootstrap";
import MyToast from "../MyToast";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit, faTrash, faUsers} from "@fortawesome/free-solid-svg-icons";
import React, { useState } from "react";
import {ExportButton} from "../tools/ExportButton";
import Pagination from "../fragments/Pagination";
import ConfirmAlert from "../fragments/ConfirmAlert";

export default function UtilPlan(props) {

    const {
        error, users = [], currentPage, usersPerPage, getExport, editUser, deleteUser,
        message, resetMessage, setPage
    } = props.data

    const lastIndex = currentPage * usersPerPage;
    const firstIndex = lastIndex - usersPerPage;
    const currentUsers = users.slice(firstIndex, lastIndex);
    const totalPages = users.length !== 0 ? Math.ceil(users.length / usersPerPage) : 1;

    return (
        <div>
            <div style={{"display": error ? "block" : "none"}}>
                <Jumbotron className="bg-dark text-white">
                    <h3>{error}</h3>
                </Jumbotron>
            </div>
            <div>
                <MyToast clear={true} message={message} type={"danger"} reset={() => resetMessage()}/>
                <div>
                    <Card className={"border border-dark bg-dark text-white"}>
                        <Card.Header>
                            <FontAwesomeIcon icon={faUsers}/>{' '}Utilization Plan
                            <ExportButton action={getExport}/>
                        </Card.Header>
                        <Card.Body>
                            <Table bordered hover striped variant="dark">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Job Title</th>
                                    <th>Department</th>
                                    <th>Level</th>
                                    <th>Direct Manager</th>
                                    <th>Projects</th>
                                    <th style={{width: "25px"}}>Utilization</th>
                                    <th>Project Leave</th>
                                    <th>Notes</th>
                                    <th style={{width: "1px"}}>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {users.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="9">No Users Available</td>
                                    </tr> :
                                    currentUsers.map((user) => (
                                        <Row key={user.id} data={user} deleteUser={deleteUser} updateUser={editUser}/>
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

    const [show, setShow] = useState(false);

    return (
        <tr>
            <td>{user.username}</td>
            <td>{user.job}</td>
            <td>{user.department}</td>
            <td>{user.level}</td>
            <td>{user.directManagerName}</td>
            <td>{user.projectNames}</td>
            <td>{user.utilization}</td>
            <td>{user.projectEnd}</td>
            <td>{user.notes}</td>
            <td style={{textAlign: "center"}}>
                <ButtonGroup>
                    <Button size="sm" variant="outline-primary" onClick={() => props.updateUser(user.id)}
                            style={{marginRight: 10}}>
                        <FontAwesomeIcon icon={faEdit}/></Button>
                    <Button size="sm" variant="outline-danger" onClick={() => setShow(prev => !prev)}>
                        <FontAwesomeIcon icon={faTrash}/></Button>
                </ButtonGroup>
                <ConfirmAlert show={show} setShow={setShow} target={user.username} action={() => props.deleteUser(user.id)}/>
            </td>
        </tr>
    )
}