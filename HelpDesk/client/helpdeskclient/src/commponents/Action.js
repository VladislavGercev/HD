import React from "react";
import { ButtonGroup } from "react-bootstrap";
import { Dropdown } from "react-bootstrap";
import { DropdownButton } from "react-bootstrap";
export default class Action extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div>
        <ButtonGroup className="d-grid gap-2 ">
          <DropdownButton
            as={ButtonGroup}
            variant="success"
            id="bg-nested-dropdown"
            title={
              this.props.changeAction.length === 0 ? "No action" : "Action"
            }
          >
            {this.props.changeAction.map((a, i) => (
              <Dropdown.Item eventKey={i}>
                <button
                  className="btn btn-success w-100"
                  value={a}
                  name={this.props.name}
                  onClick={this.props.onChangeState}
                >
                  {a}
                </button>
              </Dropdown.Item>
            ))}
          </DropdownButton>
        </ButtonGroup>
      </div>
    );
  }
}
