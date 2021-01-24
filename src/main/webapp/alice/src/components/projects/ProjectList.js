import { Button, ButtonGroup, Card, Jumbotron, Table, InputGroup, FormControl } from "react-bootstrap";
import MyToast from "../MyToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faFileContract, faPlus, faTrash, faSearch, faTimes } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import React from "react";
import { ExportButton } from "../tools/ExportButton";
import Pagination from "../fragments/Pagination";

export function ProjectList(props) {

    const {
        content, projects, search, currentPage, projectsPerPage, getExport, updateProject, deleteProject, message,
        resetMessage, toDemands, setPage, searchChange, cancelSearch, searchData, searchKeyDown
    } = props.data

    const lastIndex = currentPage * projectsPerPage;
    const firstIndex = lastIndex - projectsPerPage;
    const currentProjects = projects.slice(firstIndex, lastIndex);
    const totalPages = projects.length !== 0 ? Math.ceil(projects.length / projectsPerPage) : 1;

    const borderCss = {
        border: "1px solid #17A2B8"
    };

    return (
        <div>
            <div style={{ "display": content ? "block" : "none" }}>
                <Jumbotron className="bg-dark text-white">
                    <h3>{content}</h3>
                </Jumbotron>
            </div>
            <div>
                <MyToast clear={true} message={message} type={"danger"} reset={() => resetMessage()} />
                <div>
                    <Card className={"border border-dark bg-dark text-white"}>
                        <Card.Header>
                            <div style={{ float: "left" }}>
                                <FontAwesomeIcon icon={faFileContract} />{' '}Project List
                            </div>
                            <ExportButton action={getExport} />
                            <Link to={"/project"} style={{ float: "right", marginRight: 10 }}
                                className={"ml-auto btn btn-sm btn-success"}>
                                <FontAwesomeIcon icon={faPlus} /> Project
                            </Link>
                            <InputGroup size="sm" style={{ float: "right", width: "10%", marginRight: 10 }}>
                                <FormControl placeholder="Search" name="search" value={search}
                                    className={"bg-dark text-white"} style={borderCss}
                                    onChange={searchChange} onKeyDown={searchKeyDown} />
                                <InputGroup.Append>
                                    <Button size="sm" variant="outline-info" type="button"
                                        onClick={searchData}>
                                        <FontAwesomeIcon icon={faSearch} />
                                    </Button>
                                    <Button size="sm" variant="outline-danger" type="button" onClick={cancelSearch} style={{ marginLeft: 2 }}>
                                        <FontAwesomeIcon icon={faTimes} />
                                    </Button>
                                </InputGroup.Append>
                            </InputGroup>
                        </Card.Header>
                        <Card.Body>
                            <Table bordered hover striped variant="dark" style={{ fontSize: "14px"}}>
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Sap Number</th>
                                        <th>Phase</th>
                                        <th>Status</th>
                                        <th>Manager</th>
                                        <th>Backup Manager</th>
                                        <th>Owner</th>
                                        <th>Customer</th>
                                        <th>BU</th>
                                        <th>BU HU</th>
                                        <th>Contact Person</th>
                                        <th>FTE</th>
                                        <th>Start</th>
                                        <th>End</th>
                                        <th>Order Type</th>
                                        <th>Project Type</th>
                                        <th>Description</th>
                                        <th>Comment</th>
                                        <th style={{ width: "1px" }}>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {projects.length === 0 ?
                                        <tr align="center">
                                            <td colSpan="19">No Projects Available.</td>
                                        </tr>
                                        : currentProjects.map((project) => (
                                            <Row key={project.id} data={project} deleteProject={deleteProject}
                                                updateProject={updateProject} toDemands={toDemands} itemsPerPage={projectsPerPage} />
                                        ))
                                    }
                                </tbody>
                            </Table>
                        </Card.Body>
                        <Card.Footer>
                            <Pagination data={{
                                currentPage: currentPage,
                                setPage: setPage,
                                totalPages: totalPages,
                                itemCount: projects.length,
                                itemsPerPage: projectsPerPage
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

    const project = props.data;

    return (
        <tr>
            <td>{project.projectName}</td>
            <td>{project.sap}</td>
            <td>{project.phase}</td>
            <td>{project.status}</td>
            <td>{project.manager}</td>
            <td>{project.backupManager}</td>
            <td>{project.owner}</td>
            <td>{project.customer}</td>
            <td>{project.bu}</td>
            <td>{project.buHu}</td>
            <td>{project.contactPerson}</td>
            <td>{project.fte}</td>
            <td>{project.start.split('T')[0]}</td>
            <td>{project.end.split('T')[0]}</td>
            <td>{project.orderType}</td>
            <td>{project.projectType}</td>
            <td>{project.description}</td>
            <td>{project.comment}</td>
            <td style={{ textAlign: "center" }}>
                <ButtonGroup>
                    <Button size="sm"
                        onClick={() => props.toDemands(project.id)}
                        style={{ marginRight: 10, fontWeight: "bold" }}
                        variant="outline-warning">D
                    </Button>
                    <Button size="sm"
                        variant="outline-primary"
                        onClick={() => props.updateProject(project.id)}
                        style={{ marginRight: 10 }}>
                        <FontAwesomeIcon icon={faEdit} />
                    </Button>
                    <Button size="sm"
                        variant="outline-danger"
                        onClick={() => props.deleteProject(project.id)}>
                        <FontAwesomeIcon icon={faTrash} />
                    </Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}