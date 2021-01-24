import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import React from "react"
import { Button, FormControl, InputGroup } from "react-bootstrap"
import { faStepBackward, faFastBackward, faStepForward, faFastForward } from "@fortawesome/free-solid-svg-icons";

export default function Pagination(props) {

    let { currentPage, setPage, totalPages, itemCount, itemsPerPage } = props.data

    const pageNumCss = {
        width: "45px",
        border: "1px solid #17A2B8",
        color: "#17A2B8",
        textAlign: "center",
        fontWeight: "bold"
    }

    function firstPage() {
        if (currentPage > 1) setPage(currentPage = 1)
    }

    function prevPage() {
        if (currentPage > 1) setPage(currentPage - 1)
    }

    function nextPage() {
        if (currentPage < Math.ceil(itemCount / itemsPerPage)) setPage(currentPage + 1)
    }

    function lastPage() {
        if (currentPage < Math.ceil(itemCount / itemsPerPage)) setPage(Math.ceil(itemCount / itemsPerPage))
    }

    function changePage(event) {
        const numberRegex = /^[0-9\b]+$/;
        const { value } = event.target;
        if (numberRegex.test(value)) {
            if (value > totalPages) setPage(totalPages)
            else if (value === '0') setPage(1)
            else setPage(value)
        }
    }

    return (
        <>
            <div style={{ "float": "left" }}>
                Showing Page {currentPage} of {totalPages}
            </div>
            <div style={{
                "float": "right",
                display: itemCount <= itemsPerPage ? "none" : "block"
            }}>
                <InputGroup size="sm">
                    <InputGroup.Prepend>
                        <Button type="button" variant="outline-info"
                            disabled={currentPage === 1}
                            onClick={firstPage}>
                            <FontAwesomeIcon icon={faFastBackward} /> First
                        </Button>
                        <Button type="button" variant="outline-info"
                            disabled={currentPage === 1}
                            onClick={prevPage}>
                            <FontAwesomeIcon icon={faStepBackward} /> Prev
                        </Button>
                    </InputGroup.Prepend>
                    <FormControl style={pageNumCss}
                        value={currentPage}
                        onChange={changePage} />
                    <InputGroup.Append>
                        <Button type="button" variant="outline-info"
                            disabled={currentPage === totalPages}
                            onClick={nextPage}>
                            <FontAwesomeIcon icon={faStepForward} /> Next
                        </Button>
                        <Button type="button" variant="outline-info"
                            disabled={currentPage === totalPages}
                            onClick={lastPage}>
                            <FontAwesomeIcon icon={faFastForward} /> Last
                        </Button>
                    </InputGroup.Append>
                </InputGroup>
            </div>
        </>
    )
}