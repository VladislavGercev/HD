import React from "react";
import SearchInput from "../commponents/SearchInput";
import TicketsTable from "../commponents/TicketsTable";
import axios from "axios";
class TicketsView extends React.Component {
  constructor(props) {
    super(props);
    this.viewMy = this.viewMy.bind(this);
    this.viewAll = this.viewAll.bind(this);
    this.onFilter = this.onFilter.bind(this);
    this.onSort = this.onSort.bind(this);
    this.currentTickets = this.currentTickets.bind(this);
    this.state = {
      view: "all",
      buttonMy: "btn btn-light w-100 m-4",
      buttonAll: "btn btn-primary w-100 m-4",
      ticketsPrint: this.props.tickets,
      user: JSON.parse(localStorage.User),
    };
  }

  currentTickets() {
    return this.state.view === "all"
      ? this.setState({ ticketsPrint: this.props.tickets })
      : this.setState({ ticketsPrint: this.props.myTickets });
  }

  componentDidMount() {
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ ticketsPrint: response.data });
      });
  }

  onFilter(event) {
    let array =
      this.state.view === "all" ? this.props.tickets : this.props.myTickets;
    if (array != 0) {
      let arrayFilter = array.filter(
        (t) =>
          t.id == event.target.value ||
          t.name.toLowerCase().includes(event.target.value.toLowerCase()) ||
          t.urgency.toLowerCase().includes(event.target.value.toLowerCase()) ||
          t.state.toLowerCase().includes(event.target.value.toLowerCase()) ||
          new Date(
            t.desiredResolutionDate?.dayOfMonth +
              "/" +
              t.desiredResolutionDate?.monthValue +
              "/" +
              t.desiredResolutionDate?.year
          )
            .toLocaleDateString()
            .includes(event.target.value)
      );
      this.setState({
        ticketsPrint: arrayFilter,
      });
    }
  }

  viewMy() {
    this.setState({ view: "my" });
    this.setState({ buttonAll: "btn btn-light w-100 m-4" });
    this.setState({ buttonMy: "btn btn-primary w-100 m-4" });
    this.setState({ ticketsPrint: this.props.myTickets });
  }
  viewAll() {
    this.setState({ view: "all" });
    this.setState({ buttonMy: "btn btn-light w-100 m-4" });
    this.setState({ buttonAll: "btn btn-primary w-100 m-4" });
    this.setState({ ticketsPrint: this.props.tickets });
  }

  render() {
    const { toCreate } = this.props;
    return (
      <div className="container">
        <div className="row">
          <div className="my-5">
            <input
              type="button"
              className="btn btn-success"
              value="Logout"
              onClick={this.props.logout}
            ></input>
          </div>
        </div>
        <div className="row">
          <div className="col-md-8"></div>
          <div className="col-md-4 my-5">
            <input
              type="button"
              className="btn btn-success p-3 w-100"
              onClick={toCreate}
              value="Create New Ticket"
            ></input>
          </div>
        </div>
        <div className="container">
          <div className="row">
            <div className="col-md-6">
              <input
                type="button"
                className={this.state.buttonAll}
                onClick={this.viewAll}
                value="All Tickets"
              ></input>
            </div>
            <div className="col-md-6">
              <input
                type="button"
                className={this.state.buttonMy}
                onClick={this.viewMy}
                value="My Tickets"
              ></input>
            </div>
          </div>
        </div>
        <SearchInput
          inputValue={this.props.inputValue}
          onFilter={this.onFilter}
        ></SearchInput>
        <TicketsTable
          onChangeState={this.props.onChangeState}
          onChangeAction={this.props.onChangeAction}
          tickets={this.state.ticketsPrint}
          onSort={this.onSort}
        ></TicketsTable>
      </div>
    );
  }

  onSort(e) {
    let order = ["LOW", "AVERAGE", "HIGH", "CRITICAL"];
    let ticketsSort = this.state.ticketsPrint;
    switch (e.target.id) {
      case "idUp":
        ticketsSort.sort((a, b) => a.id - b.id);
        console.log("UP");
        break;
      case "idDown":
        ticketsSort.sort((a, b) => b.id - a.id);
        console.log("DOWN");
        break;
      case "nameUp":
        ticketsSort.sort((a, b) => a.name.localeCompare(b.name));
        break;
      case "nameDown":
        ticketsSort.sort((a, b) => b.name.localeCompare(a.name));
        break;
      case "dateUp":
        ticketsSort.sort(
          (a, b) =>
            new Date(a.desiredResolutionDate) -
            new Date(b.desiredResolutionDate)
        );
        break;
      case "dateDown":
        ticketsSort.sort(
          (a, b) =>
            new Date(b.desiredResolutionDate) -
            new Date(a.desiredResolutionDate)
        );
        break;
      case "UrgencyUp":
        ticketsSort.sort(
          (a, b) => order.indexOf(b.urgency) - order.indexOf(a.urgency)
        );
        break;
      case "UrgencyDown":
        ticketsSort.sort(
          (a, b) => order.indexOf(a.urgency) - order.indexOf(b.urgency)
        );
        break;
      case "StatusUp":
        ticketsSort.sort((a, b) => a.state.localeCompare(b.state));
        break;
      case "StatusDown":
        ticketsSort.sort((a, b) => b.state.localeCompare(a.state));
        break;
    }
    this.setState({ ticketsPrint: ticketsSort });
  }
}

export default TicketsView;
