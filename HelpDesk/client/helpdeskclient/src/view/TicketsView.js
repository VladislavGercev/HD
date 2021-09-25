import React from "react";
import SearchInput from "../commponents/SearchInput";
import TicketsTable from "../commponents/TicketsTable";
class TicketsView extends React.Component {
  constructor(props) {
    super(props);
    this.viewMy = this.viewMy.bind(this);
    this.viewAll = this.viewAll.bind(this);

    this.state = {
      view: "all",
      buttonMy: "btn btn-light w-100 m-4",
      buttonAll: "btn btn-primary w-100 m-4",
    };
  }

  viewMy() {
    this.setState({ view: "my" });
    this.setState({ buttonAll: "btn btn-light w-100 m-4" });
    this.setState({ buttonMy: "btn btn-primary w-100 m-4" });
  }
  viewAll() {
    this.setState({ view: "all" });
    this.setState({ buttonMy: "btn btn-light w-100 m-4" });
    this.setState({ buttonAll: "btn btn-primary w-100 m-4" });
  }
  curentView() {
    if (this.state.view === "my") {
      return this.props.myTickets;
    } else {
      return this.props.tickets;
    }
  }

  render() {
    const { onHandleChange, toCreate } = this.props;
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
        <SearchInput></SearchInput>
        <TicketsTable
          onChangeState={this.props.onChangeState}
          onChangeAction={this.props.onChangeAction}
          tickets={this.curentView()}
          onSortId={this.props.onSortId}
        ></TicketsTable>
      </div>
    );
  }
}
export default TicketsView;
