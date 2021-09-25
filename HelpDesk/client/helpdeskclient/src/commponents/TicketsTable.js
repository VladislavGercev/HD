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
                <button
                  className="btn btn-outline-light btn-sm"
                  onClick={this.props.onSortId}
                >
                  <div>
                    <div class="triangle-up"></div>
                    <div class="triangle-down"></div>
                  </div>
                </button>
              </th>
              <th>
                Name{" "}
                <button className="btn btn-outline-light btn-sm">
                  <div>
                    <div class="triangle-up"></div>
                    <div class="triangle-down"></div>
                  </div>
                </button>
              </th>
              <th>
                Desired Date
                <button className="btn btn-outline-light btn-sm">
                  <div>
                    <div class="triangle-up"></div>
                    <div class="triangle-down"></div>
                  </div>
                </button>
              </th>
              <th>
                Urgency
                <button className="btn btn-outline-light btn-sm">
                  <div>
                    <div class="triangle-up"></div>
                    <div class="triangle-down"></div>
                  </div>
                </button>
              </th>
              <th>
                Status
                <button className="btn btn-outline-light btn-sm">
                  <div>
                    <div class="triangle-up"></div>
                    <div class="triangle-down"></div>
                  </div>
                </button>
              </th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.props.tickets.map((ticket) => (
              <tr>
                <td>{ticket.id}</td>
                <td>
                  <a href={"/tickets/" + ticket.id}>{ticket.name}</a>
                </td>
                <td>{ticket.desired_resolution_date}</td>
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
