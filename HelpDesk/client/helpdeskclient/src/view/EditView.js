import React from "react";
import Form from "../commponents/Form";

class EditView extends React.Component {
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
                onClick={this.props.toTicketOverview}
              ></input>
            </div>
            <div className="col-md-1"></div>
            <div className="col-md-4 my-5">
              <h2>{"Edit Ticket (" + this.props.ticketId + ")"}</h2>
            </div>
          </div>
        </div>
        <Form
          ticket={this.props.ticket}
          attachments={this.props.attachments}
          ticketId={this.props.ticketId}
          onDeleteFile={this.props.onDeleteFile}
          onHandleChange={this.props.onHandleChange}
          onHandleChangeAttachment={this.props.onHandleChangeAttachment}
          draftTicket={this.props.draftTicket}
          newTicket={this.props.newTicket}
        ></Form>
      </div>
    );
  }
}

export default EditView;
