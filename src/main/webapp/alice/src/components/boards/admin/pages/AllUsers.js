import React, {useState} from 'react';
import {Button, ButtonGroup, Card, Jumbotron, Table} from "react-bootstrap";
import * as IoIcons from "react-icons/io";
import MyToast from "../../../MyToast";
import Pagination from "../../../fragments/Pagination";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck, faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import ConfirmAlert from "../../../fragments/ConfirmAlert";

function AllUsers(props) {

    const {
        error, users, currentPage, usersPerPage, getExport, chkLastValidation, editUser,
        deleteUser, messageType, message, resetMessage, setPage
    } = props.data

    const lastIndex = currentPage * usersPerPage;
    const firstIndex = lastIndex - usersPerPage;
    const currentUsers = users.slice(firstIndex, lastIndex);
    const totalPages = users.length !== 0 ? Math.ceil(users.length / usersPerPage) : 1;

    return (
            <>
                <div style={{"display": error ? "block" : "none"}}>
                    <Jumbotron className="bg-dark text-white">
                        <h3>{error}</h3>
                    </Jumbotron>
                </div>
                <div>
                    <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()}/>
                    <Card className={"border border-dark bg-dark text-white admin-card"}>
                        <Card.Header>
                            <IoIcons.IoIosPaper/>{' '}Users
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
                                    <th>Manager</th>
                                    <th>Projects</th>
                                    <th>Utilization</th>
                                    <th>Project Leave</th>
                                    <th>Notes</th>
                                    <th>Last Validation</th>
                                    <th style={{width: "1px"}}>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {currentUsers.length === 0 ?
                                        <tr align="center">
                                            <td colSpan="13">No Users Available</td>
                                        </tr> :
                                        currentUsers.map((user) => (
                                                <TableRow key={user.id} data={user} deleteUser={deleteUser} updateUser={editUser}/>
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
            </>
    );
}

function TableRow(props) {

    const user = props.data

    const [show, setShow] = useState(false);

    return (
            <tr>
                <td>{user.username}</td>
                <td>{user.job}</td>
                <td>{user.department}</td>
                <td>{user.email}</td>
                <td>{user.city}</td>
                <td>{user.level}</td>
                <td>{user.directManager}</td>
                <td>{user.projectNames}</td>
                <td>{user.utilization}</td>
                <td>{user.projectEnd}</td>
                <td>{user.notes}</td>
                <td>{user.lastValidation}</td>
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

export default AllUsers;