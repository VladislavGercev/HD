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
        <div class="container  my-5">
          <div class="row">
            <h2>
              Ticket ({ticket.id}) - {ticket.name}
            </h2>
          </div>
          <div class="row">
            <span class="col-4 text-right">Created on:</span>
            <span class="col-2">
            {ticket.desiredResolutionDate != null
                ? ticket.createdOn[0] +
                  "/" +
                  ticket.createdOn[1] +
                  "/" +
                  ticket.createdOn[2]
                : ""}
            </span>
          </div>
          <div class="row">
            <span class="col-4 text-right">Status:</span>
            <span class="col-2">{ticket.state}</span>
            <span class="col-2   text-right">Category:</span>
            <span class="col-4">{ticket.category?.name}</span>
          </div>
          <div class="row">
            <span class="col-4 text-right">Urgency:</span>
            <span class="col-2">{ticket.urgency}</span>
          </div>
          <div class="row">
            <span class="col-4 text-right">Desired resolution date:</span>
            <span class="col-4">
              {ticket.desiredResolutionDate != null
                ? ticket.desiredResolutionDate[0] +
                  "/" +
                  ticket.desiredResolutionDate[1] +
                  "/" +
                  ticket.desiredResolutionDate[2]
                : ""}
            </span>
          </div>
          <div class="row">
            <span class="col-4 text-right">Owner:</span>
            <span class="col-6">
              {ticket.owner?.firstName + " " + ticket.owner?.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Approver:</span>
            <span class="col-6">
              {ticket.approver &&
                ticket.approver.firstName + " " + ticket.approver.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right">Assignee:</span>
            <span class="col-6">
              {ticket.assignee &&
                ticket.assignee.firstName + " " + ticket.assignee.lastName}
            </span>
          </div>
          <div class="row">
            <span class="col-2 text-right my-2  ">Attachments:</span>
            <div>
              {this.props.attachments &&
                this.props.attachments.map((a) => (
                  <div style={{display: "inline"}}>
                    <button
                      lable="Download"
                      name={a.name}
                      id={a.id}
                      onClick={this.props.onDownLoad}
                      className="btn btn-outline-primary btn-sm"
                    >
                      {a.name}
                    </button>
                  </div>
                ))}
            </div>
          </div>
          <div class="row">
            <span class="col-2 text-right my-2">Description:</span>
            <textarea
              class="col-6 bg-light border-0 my-2"
              value={ticket.description}
            ></textarea>
          </div>
        </div>
      )
    );
  }
}

export default TicketInfo;
