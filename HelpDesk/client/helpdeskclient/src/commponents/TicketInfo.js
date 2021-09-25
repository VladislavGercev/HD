import React from "react";

class TicketInfo extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    let ticket = this?.props?.ticket;
    return (
      ticket && (
        <div class="container w-75 my-5">
          <div class="row">
            <h2>
              Ticket ({ticket.id}) - {ticket.name}
            </h2>
          </div>
          <div class="row">
            <span class="col-2 text-right">Created on:</span>
            <span class="col-2">
              {new Date(ticket.createdOn).toLocaleDateString()}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Status:</span>
            <span class="col-2">{ticket.state}</span>
            <span class="col-2 text-right">Category:</span>
            <span class="col-6">{ticket?.category?.name}</span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Urgency:</span>
            <span class="col-2">{ticket.urgency}</span>
          </div>
          <div class="row">
            <span class="col-4 text-right">Desired resolution date:</span>
            <span class="col-4">{ticket.desired_resolution_date}</span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Owner:</span>
            <span class="col-6">
              {ticket?.owner?.firstName + " " + ticket?.owner?.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Approver:</span>
            <span class="col-2">
              {ticket.approver &&
                ticket.approver.firstName + " " + ticket.approver.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Assignee:</span>
            <span class="col-2">
              {ticket.assignee &&
                ticket.assignee.firstName + " " + ticket.assignee.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Attachments:</span>
            <div>
              {this.props.attachments &&
                this.props.attachments.map((a) => (
                  <div>
                  <button lable='Download' name={a.name} id={a.id} onClick={this.props.onDownLoad}  className="btn btn-outline-primary btn-sm">{a.name}</button>
                  </div>
                  
                  
                ))}
            </div>
          </div>
          <div class="row">
            <span class="col-2 text-right">Description:</span>
            <textarea
              class="col-6 bg-light border-0"
              value={ticket.description}
            ></textarea>
          </div>
        </div>
      )
    );
  }
}

export default TicketInfo;
