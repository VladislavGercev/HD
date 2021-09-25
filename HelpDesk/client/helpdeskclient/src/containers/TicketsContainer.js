import React, { Component } from "react";
import TicketsView from "../view/TicketsView";
import axios from "axios";
import history from "../history";

class Tickets extends Component {
  constructor(props) {
    super(props);
    this.getMyTickets = this.getMyTickets.bind(this);
    this.onChangeAction = this.onChangeAction.bind(this);
    this.onChangeState = this.onChangeState.bind(this);
    this.onAction = this.onAction.bind(this);
    this.logout = this.logout.bind(this);
    this.state = {
      user: JSON.parse(localStorage.User),
      tickets: [],
      myTickets: [],

    };
  }

  toCreateTicket() {
    history.push("/tickets/new");
  }

  logout() {
    localStorage.removeItem("AuthHeader");
    localStorage.removeItem("User");
    history.push("/login");
  }

  componentDidMount() {
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ tickets: response.data });
        this.getMyTickets();
      });
  }

  getMyTickets() {
    var result = [];
    switch (this.state.user.role) {
      case "EMPLOYEE":
        result = this.state.tickets;
        break;
      case "MANAGER":
        result = this.state.tickets.filter(
          (t) =>
            t.owner.id === this.state.user.id ||
            (t.approver
              ? t.approver.id === this.state.user.id && t.state === "APPROVED"
              : false)
        );
        break;
      case "ENGINEER":
        result = this.state.tickets.filter((t) =>
          t.assignee ? t.assignee.id === this.state.user.id : false
        );
        break;
    }
    this.setState({ myTickets: result });
  }

  onChangeAction(ticket) {
    var result = [];
    switch (this.state.user.role) {
      case "EMPLOYEE":
        if (ticket.state === "DRAFT" || ticket.state === "DECLINED") {
          result = ["Submit", "Cancel"];
        }
        break;
      case "MANAGER":
        if (ticket.owner.id === this.state.user.id) {
          if (ticket.state === "DRAFT" || ticket.state === "DECLINED") {
            result = ["Submit", "Cancel"];
          }
        } else if (ticket.state === "NEW") {
          result = ["Approve", "Decline", "Cancel"];
        }
        break;
      case "ENGINEER":
        if (ticket.state === "APPROVED") {
          result = ["Assing to Me", "Cancel"];
        }
        if (ticket.state === "IN_PROGRESS") {
          result = ["Done"];
        }
        break;
    }
    return result;
  }

  onAction(data) {
    var result = "";
    switch (data) {
      case "Submit":
        result = "NEW";
        break;
      case "Approve":
        result = "APPROVED";
        break;
      case "Decline":
        result = "DECLINED";
        break;
      case "Cancel":
        result = "CANCELED";
        break;
      case "Assing to Me":
        result = "IN_PROGRESS";
        break;
      case "Done":
        result = "DONE";
        break;
    }

    return result;
  }

  onChangeState(e) {
    var status = this.onAction(e.target.value);
    var ticketId = e.target.name;
    var ticket = this.state.tickets.filter((t) => t.id == ticketId)[0];
    ticket.state = status;
    var newTickets = this.state.tickets.filter((t) => t.id != ticketId);
    this.setState({ tickets: [...newTickets, ticket] });

    axios
      .put(
        "http://localhost:8099/HelpDesk/tickets/" + ticketId + "/" + status,
        null,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((responce) => {})
      .catch((error) => {});
  }

  onMyTickets() {
    var result = [];
    switch (this.state.user.role) {
      case "EMPLOYEE":
        result = this.state.tickets;
        break;
      case "MANAGER":
        result = this.state.tickets.filter(
          (t) =>
            t.owner.id === this.state.user.id ||
            (t.approver
              ? t.approver.id === this.state.user.id && t.state === "APPROVED"
              : false)
        );
        break;
      case "ENGINEER":
        result = this.state.tickets.filter((t) =>
          t.assignee ? t.assignee.id === this.state.user.id : false
        );
        break;
    }

    return result;
  }
  render() {
    return (
      <TicketsView
        logout={this.logout}
        tickets={this.state.tickets}
        toCreate={this.toCreateTicket}
        myTickets={this.state.myTickets}
        onChangeState={this.onChangeState}
        onChangeAction={this.onChangeAction ? this.onChangeAction : []}
        onSortId={this.onSortId}
      ></TicketsView>
    );
  }


}
export default Tickets;
