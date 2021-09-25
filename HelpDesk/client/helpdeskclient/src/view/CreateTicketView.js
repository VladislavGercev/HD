import React from "react";
import Form from "../commponents/Form";

class CreateTicketView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div>
        <div className="container">
          <div className="row">
            <div className="col-md-2 my-5">
              <input
                type="button"
                className="btn btn-success p-1 w-100"
                value="Tickets"
                onClick={this.props.toTickets}
              ></input>
            </div>
            <div className="col-md-1"></div>
            <div className="col-md-4 my-5">
              <h2>Create new Ticket</h2>
            </div>
          </div>
        </div>
        <Form
          onDeleteFile={this.props.onDeleteFile}
          attachments={this.props.attachments}
          onHandleChangeAttachment={this.props.onHandleChangeAttachment}
          onHandleChange={this.props.onHandleChange}
          newTicket={this.props.newTicket}
          draftTicket={this.props.draftTicket}
        ></Form>
        ;
      </div>
    );
  }
}

export default CreateTicketView;
