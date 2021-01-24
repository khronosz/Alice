import { Col, Form } from "react-bootstrap";
import { toCapitalize } from "./StringUtils";
import React from "react";
import { Typeahead } from "react-bootstrap-typeahead";

export function TextField(props) {

    const { name, variable = "", change, req = true, type = "text" } = props

    return (
        <Form.Group as={Col} controlId={"formGrid" + name}>
            <Form.Label>{toCapitalize(name)}</Form.Label>
            <Form.Control required={req}
                autoComplete="off"
                type={type}
                name={name}
                value={variable}
                onChange={change}
                className={"bg-dark text-white"}
                placeholder={"Enter " + toCapitalize(name)}
            />
        </Form.Group>
    )
}

export function DropDownMenu(props) {

    const { name, variable, change, options, json = false, field } = props

    return (
        <Form.Group as={Col} controlId={"formGrid " + name}>
            <Form.Label>{toCapitalize(name)}</Form.Label>
            <Form.Control required
                autoComplete="off"
                as="select"
                name={name}
                value={json ? JSON.stringify(variable) : variable}
                onChange={change}
                className={"bg-dark text-white"}>
                <option key={"Empty" + name} value="">Select...</option>
                {options.map((opt, index) => (
                    <option key={index} value={json ? JSON.stringify(opt) : opt}>
                        {json ? opt[field] : opt}
                    </option>
                ))}
            </Form.Control>
        </Form.Group>
    )
}

export function DatePicker(props) {

    const { name, variable, change, req = true } = props

    return (
        <Form.Group as={Col} controlId={"formGrid" + name}>
            <Form.Label>{toCapitalize(name)}</Form.Label>
            <Form.Control required={req}
                autoComplete="off"
                type="date"
                name={name}
                value={variable}
                onChange={change}
                className={"bg-dark text-white"}
                placeholder={"Enter " + toCapitalize(name)}
            />
        </Form.Group>
    )
}

export function FilterList(props) {

    const { options, change, selected, name } = props
    
    const index = selected ? options.indexOf(selected) : -1

    return (
        <Form.Group as={Col} controlId={"formGrid" + name}>
            <Form.Label>{toCapitalize(name)}</Form.Label>
            <Typeahead
                id={"typeahead" + name}
                onChange={change}
                options={options}
                labelKey={name => name}
                selected={options.slice(index, index + 1)}
                placeholder={"Select..."}
            />
        </Form.Group>
    )
}