import React from "react";
import Action from "./Action";

class TicketsTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="container">
        <table className="table table-hover table-bordered">
          <thead>
            <tr className="table-active">
              <th>
                ID
                <div style={{ display: "inline-block" }}>
                  <div
                    className="triangle-up"
                    id="idUp"
                    onClick={this.props.onSort}
                  ></div>
                  <div
                    className="triangle-down"
                    id="idDown"
                    onClick={this.props.onSort}
                  ></div>
                </div>
              </th>
              <th>
                Name
                <div style={{ display: "inline-block" }}>
                  <div
                    className="triangle-up"
                    id="nameUp"
                    onClick={this.props.onSort}
                  ></div>
                  <div
                    className="triangle-down"
                    id="nameDown"
                    onClick={this.props.onSort}
                  ></div>
                </div>
              </th>
              <th>
                Desired Date
                <div style={{ display: "inline-block" }}>
                  <div
                    className="triangle-up"
                    id="dateUp"
                    onClick={this.props.onSort}
                  ></div>
                  <div
                    className="triangle-down"
                    id="dateDown"
                    onClick={this.props.onSort}
                  ></div>
                </div>
              </th>
              <th>
                Urgency
                <div style={{ display: "inline-block" }}>
                  <div
                    className="triangle-up"
                    id="UrgencyUp"
                    onClick={this.props.onSort}
                  ></div>
                  <div
                    className="triangle-down"
                    id="UrgencyDown"
                    onClick={this.props.onSort}
                  ></div>
                </div>
              </th>
              <th>
                Status
                <div style={{ display: "inline-block" }}>
                  <div
                    className="triangle-up"
                    id="StatusUp"
                    onClick={this.props.onSort}
                  ></div>
                  <div
                    className="triangle-down"
                    id="StatusDown"
                    onClick={this.props.onSort}
                  ></div>
                </div>
              </th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.props.tickets?.map((ticket) => (
              <tr key={ticket.id}>
                <td>{ticket.id }</td>
                <td>
                  <a href={"/tickets/" + ticket.id}>{ticket.name}</a>
                </td>
                <td>
                  {ticket.desiredResolutionDate!==null?ticket.desiredResolutionDate[0]+"/" +ticket.desiredResolutionDate[1]+"/"+ticket.desiredResolutionDate[2]:""}
                </td>
                <td>{ticket.urgency}</td>
                <td>{ticket.state}</td>
                <td>
                  <Action
                    name={ticket.id}
                    onChangeState={this.props.onChangeState}
                    changeAction={this.props.onChangeAction(ticket)}
                  ></Action>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default TicketsTable;
