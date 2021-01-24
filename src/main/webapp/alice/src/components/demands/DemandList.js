import { Button, ButtonGroup, Card, Jumbotron, Table } from "react-bootstrap";
import MyToast from "../MyToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faPlus, faTrash, faUndo, faGavel, faChevronLeft, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import React from "react";
import { ExportButton } from "../tools/ExportButton";
import Pagination from "../fragments/Pagination";

export default function DemandList(props) {

    const { content, message, demands, currentPage, demandsPerPage, getExport, editDemand,
        deleteDemand, project, toProjects, resetMessage, setPage, idx, projectIds, nextBtn, prevBtn } = props.data

    const lastIndex = currentPage * demandsPerPage;
    const firstIndex = lastIndex - demandsPerPage;
    const currentDemands = demands.slice(firstIndex, lastIndex);
    const totalPages = demands.length !== 0 ? Math.ceil(demands.length / demandsPerPage) : 1;

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
                    <Button size="sm" variant="" type="button" onClick={() => prevBtn()} className="chevron-button"
                    style={{ display: idx === 0 ? 'none' : 'inline-block', position: "absolute", top: "45%", left: "7%" }}>
                        <FontAwesomeIcon icon={faChevronLeft} size="3x" color="white"/>
                    </Button>
                    <Card className={"border border-dark bg-dark text-white"} style={{ width: '70vw'}}>
                        <Card.Header>
                            <FontAwesomeIcon icon={faGavel} />{" Demands of " + project.projectName}
                            <Button size="sm" variant="info" type="button" style={{ float: "right", marginLeft: 10 }} onClick={toProjects}>
                                <FontAwesomeIcon icon={faUndo} /> Back
                            </Button>
                            <ExportButton action={getExport} />
                            <Link to={"/project/" + projectIds[idx] + "/demand"}
                                style={{ float: "right", marginRight: 10 }}
                                className={"ml-auto btn btn-sm btn-success"}>
                                <FontAwesomeIcon icon={faPlus} /> Demand
                            </Link>
                        </Card.Header>
                        <Card.Body>
                            <Table bordered hover striped variant="dark">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Mib</th>
                                        <th>User</th>
                                        <th>Utilization</th>
                                        <th>Project Join</th>
                                        <th>Project Leave</th>
                                        <th style={{ width: "1px" }}>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {demands.length === 0 ?
                                        <tr align="center">
                                            <td colSpan="7">No Demands Available.</td>
                                        </tr>
                                        : currentDemands.map((demand) => (
                                            <Row key={demand.id} data={demand} deleteDemand={deleteDemand} editDemand={editDemand} />
                                        ))
                                    }
                                </tbody>
                            </Table>
                        </Card.Body>
                        <Card.Footer style={{ "textAlign": "right" }}>
                            <Pagination data={{
                                currentPage: currentPage,
                                setPage: setPage,
                                totalPages: totalPages,
                                itemCount: demands.length,
                                itemsPerPage: demandsPerPage
                            }}
                            />
                        </Card.Footer>
                    </Card>
                    <Button size="sm" variant="" type="button" onClick={() => nextBtn()} className="chevron-button"
                    style={{ display: idx === projectIds.length - 1 ? 'none' : 'inline-block', position: "absolute", top: "45%", right: "7%" }}>
                        <FontAwesomeIcon icon={faChevronRight}  size="3x" color="white"/>
                    </Button>
                </div>
            </div>
        </div>
    )
}

function Row(props) {

    const demand = props.data

    return (
        <tr>
            <td>{demand.name}</td>
            <td>{demand.mib}</td>
            <td>{demand.username}</td>
            <td>{demand.utilization}</td>
            <td>{demand.projectStart}</td>
            <td>{demand.projectEnd}</td>
            <td style={{ textAlign: "center" }}>
                <ButtonGroup>
                    <Button size="sm" variant="outline-primary" onClick={() => props.editDemand(demand.id)} style={{ marginRight: 10 }}>
                        <FontAwesomeIcon icon={faEdit} />
                    </Button>
                    <Button size="sm" variant="outline-danger" onClick={() => props.deleteDemand(demand.id)}>
                        <FontAwesomeIcon icon={faTrash} /></Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}