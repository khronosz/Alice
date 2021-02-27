import Button from "react-bootstrap/Button";
import {Modal} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash, faUndo} from "@fortawesome/free-solid-svg-icons";
import React from "react";

const ConfirmAlert = (props) => {

  const {show, setShow, target, action} = props;

  const handleClose = () => setShow(false);

  return (
    <Modal size="sm" show={show} onHide={handleClose} animation={true} centered>
      <Modal.Header closeButton>
        <Modal.Title>Delete {target}</Modal.Title>
      </Modal.Header>
      <Modal.Body>Are you sure you want to delete?</Modal.Body>
      <Modal.Footer>
        <Button size="sm" style={{marginRight: "auto"}} variant="info" type="button" onClick={() => handleClose()}>
          <FontAwesomeIcon icon={faUndo}/> Back
        </Button>
        <Button size="sm" variant="danger" type="submit" onClick={() => {handleClose(); action();}}>
          <FontAwesomeIcon icon={faTrash}/> Delete
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ConfirmAlert;