import React from "react";
import MyToast from "../../MyToast";
import Card from "react-bootstrap/Card";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck, faEdit, faUser} from "@fortawesome/free-solid-svg-icons";
import {Alert, Button, ButtonGroup, Col, Container, Jumbotron, Row, Table} from "react-bootstrap";
import Pagination from "../../fragments/Pagination";

export function Dashboard(props) {

    const {
        currentUser, nonUtilizedUsers, nonValidatedUsers, projects,
        currentProjectPage, currentUtilPage, currentValidPage, itemsPerPage,
        setProjectPage, setNonUtilizedPage, setNonValidatedPage, updateProject,
        editUser, chkLastValidation, editDemand, message, messageType, resetMessage
    } = props.data;

    const lastProjectIndex = currentProjectPage * itemsPerPage;
    const firstProjectIndex = lastProjectIndex - itemsPerPage;
    const currentProjects = projects.slice(firstProjectIndex, lastProjectIndex);
    const totalProjectPages = projects.length !== 0 ? Math.ceil(projects.length / itemsPerPage) : 1;

    const lastUtilIndex = currentUtilPage * itemsPerPage;
    const firstUtilIndex = lastUtilIndex - itemsPerPage;
    const currentNonUtilizedUsers = nonUtilizedUsers.slice(firstUtilIndex, lastUtilIndex);
    const totalNonUtilizedPages = nonUtilizedUsers.length !== 0 ? Math.ceil(nonUtilizedUsers.length / itemsPerPage) : 1;

    const lastValidIndex = currentValidPage * itemsPerPage;
    const firstValidIndex = lastValidIndex - itemsPerPage;
    const currentNonValidatedUsers = nonValidatedUsers.slice(firstValidIndex, lastValidIndex);
    const totalNonValidatedPages = nonValidatedUsers.length !== 0 ? Math.ceil(nonValidatedUsers.length / itemsPerPage) : 1;

    const showProjectsTable = projects.length !== 0 ? "table" : "none";
    const showUtilTable = nonUtilizedUsers.length !== 0 ? "table" : "none";
    const showValidTable = nonValidatedUsers.length !== 0 ? "table" : "none";

    return (
        <div>
            <MyToast clear={true} message={message} type={messageType} reset={() => resetMessage()} />
            {currentUser ?
                <Card className="bg-dark text-white pt-3">
                    <Card.Header><FontAwesomeIcon icon={faUser} />{' '}{currentUser.username.charAt(0).toUpperCase() + currentUser.username.slice(1)}'s Dashboard</Card.Header>
                    <Card.Body>
                        <Container fluid>
                            <Row>
                                <Col md={3}>
                                    {projects.length !== 0 ?
                                        projects.length === 1 ?
                                            <Alert variant={'success'}>Your team is involved in 1 project!</Alert>
                                            :
                                            <Alert variant={'success'}>Your team is involved in {projects.length} projects!</Alert>
                                        :
                                        <Alert variant={'warning'}>Your team is not involved in any project!</Alert>
                                    }
                                    {nonUtilizedUsers.length !== 0 ?
                                        nonUtilizedUsers.length === 1 ?
                                            <Alert variant={'warning'}>You have 1 underutilized employee!</Alert>
                                            :
                                            <Alert variant={'warning'}>You have {nonUtilizedUsers.length} underutilized
                                                employees!</Alert>
                                        :
                                        <Alert variant={'success'}>All of your employees are fully utilized!</Alert>
                                    }
                                    {nonValidatedUsers.length !== 0 ?
                                        nonValidatedUsers.length === 1 ?
                                            <Alert variant={'warning'}>You have 1 employee with non validated data!</Alert>
                                            :
                                            <Alert variant={'warning'}>You have {nonValidatedUsers.length} employees with non validated data!</Alert>
                                        :
                                        <Alert variant={'success'}>The data of all your employees is valid!</Alert>
                                    }
                                </Col>
                                <Col md={3}>

                                </Col>
                                <Col md={6} style={{display: showProjectsTable}}>
                                    <strong>Your projects</strong><br/><br/>
                                    <Table bordered hover striped variant="dark" size="sm" style={{fontSize: "12px"}}>
                                        <thead>
                                        <tr>
                                            <th>Project Name</th>
                                            <th>Phase</th>
                                            <th>Status</th>
                                            <th>Manager</th>
                                            <th>Customer</th>
                                            <th>FTE</th>
                                            <th>End</th>
                                            <th style={{width: "1px"}}>Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {projects.length !== 0 ?
                                            currentProjects.map((project) => (
                                                <ProjectRow key={project.id} data={project} updateProject={updateProject}/>
                                            )) :
                                            <tr>
                                                <td colSpan={8}>No Projects Available</td>
                                            </tr>}
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td colSpan={8}>
                                                <Pagination data={{
                                                    currentPage: currentProjectPage,
                                                    setPage: setProjectPage,
                                                    totalPages: totalProjectPages,
                                                    itemCount: projects.length,
                                                    itemsPerPage: itemsPerPage
                                                }}
                                                />
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </Table>
                                </Col>
                            </Row><br/>
                            <Row>
                                <Col md={6} style={{display: showUtilTable}}>
                                    <strong>Underutilized employees</strong><br/><br/>
                                    <Table bordered hover striped variant="dark" size="sm" style={{fontSize: "12px"}}>
                                        <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Job Title</th>
                                            <th>Level</th>
                                            <th>Projects</th>
                                            <th style={{width: "3rem"}}>Utilization</th>
                                            <th style={{width: "1px"}}>Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {nonUtilizedUsers.length !== 0 ?
                                            currentNonUtilizedUsers.map((user) => (
                                                <NonUtilizedRow key={user.id} data={user} editDemand={editDemand}/>
                                            )) :
                                            <tr>
                                                <td colSpan={6}>No Users Available</td>
                                            </tr>}
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td colSpan={6}>
                                                <Pagination data={{
                                                    currentPage: currentUtilPage,
                                                    setPage: setNonUtilizedPage,
                                                    totalPages: totalNonUtilizedPages,
                                                    itemCount: nonUtilizedUsers.length,
                                                    itemsPerPage: itemsPerPage
                                                }}/>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </Table>
                                </Col>
                                <Col md={6} style={{display: showValidTable}}>
                                    <strong>Non validated employees</strong><br/><br/>
                                    <Table bordered hover striped variant="dark" size="sm" style={{fontSize: "12px"}}>
                                        <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Job Title</th>
                                            <th>Level</th>
                                            <th>Projects</th>
                                            <th style={{width: "6rem"}}>Last Validation</th>
                                            <th style={{width: "1px"}}>Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {nonValidatedUsers.length !== 0 ?
                                            currentNonValidatedUsers.map((user) => (
                                                <NonValidatedRow key={user.id} data={user} editUser={editUser} chkLastValidation={chkLastValidation}/>
                                            )) :
                                            <tr>
                                                <td colSpan={6}>No Users Available</td>
                                            </tr>}
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td colSpan={6}>
                                                <Pagination data={{
                                                    currentPage: currentValidPage,
                                                    setPage: setNonValidatedPage,
                                                    totalPages: totalNonValidatedPages,
                                                    itemCount: nonValidatedUsers.length,
                                                    itemsPerPage: itemsPerPage
                                                }}/>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </Table>
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

function ProjectRow(props) {

    const project = props.data

    return (
        <tr>
            <td>{project.projectName}</td>
            <td>{project.phase}</td>
            <td>{project.status}</td>
            <td>{project.manager}</td>
            <td>{project.customer}</td>
            <td>{project.fte}</td>
            <td>{project.end}</td>
            <td style={{textAlign: "center"}}>
                <Button size="sm" variant="outline-primary" onClick={() => props.updateProject(project.id)}>
                    <FontAwesomeIcon icon={faEdit}/>
                </Button>
            </td>
        </tr>
    )
}

function NonUtilizedRow(props) {

    const user = props.data

    return (
        <tr>
            <td>{user.username}</td>
            <td>{user.job}</td>
            <td>{user.level}</td>
            <td>{user.projectNames}</td>
            <td>{user.utilization}</td>
            <td style={{textAlign: "center"}}>
                <Button size="sm" variant="outline-primary" onClick={() => props.editDemand(user.id)}>
                    <FontAwesomeIcon icon={faEdit}/>
                </Button>
            </td>
        </tr>
    )
}

function NonValidatedRow(props) {

    const user = props.data

    return (
        <tr>
            <td>{user.username}</td>
            <td>{user.job}</td>
            <td>{user.level}</td>
            <td>{user.projectNames}</td>
            <td>{user.lastValidation}</td>
            <td style={{textAlign: "center"}}>
                <ButtonGroup>
                    <Button size="sm" variant="outline-primary" onClick={() => props.editUser(user.id)}>
                        <FontAwesomeIcon icon={faEdit}/>
                    </Button>
                    <Button size="sm" variant="outline-success" onClick={() => props.chkLastValidation(user.id)} style={{marginLeft: 10}}>
                        <FontAwesomeIcon icon={faCheck}/>
                    </Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}