import React from 'react'
import Button from 'react-bootstrap/Button';
import { faFileExcel } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const ExportButton = (props) => {

    return (
        <Button style={{ float: "right" }}
            className={"ml-auto btn btn-sm btn-primary"}
            onClick={props.action}>
            <FontAwesomeIcon icon={faFileExcel} />
        </Button>
    )
}