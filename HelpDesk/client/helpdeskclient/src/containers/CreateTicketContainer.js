import React from "react";
import CreateTicketView from "../view/CreateTicketView";
import axios from "axios";
import history from "../history";

class CreateTicketContainer extends React.Component {
  constructor(props) {
    super(props);
    this.toTickets = this.toTickets.bind(this);
    this.onHandleChange = this.onHandleChange.bind(this);
    this.createTicket = this.createTicket.bind(this);
    this.newTicket = this.newTicket.bind(this);
    this.draftTicket = this.draftTicket.bind(this);
    this.onHandleChangeAttachment = this.onHandleChangeAttachment.bind(this);
    this.onDeleteFile = this.onDeleteFile.bind(this)
    this.state = {
      state: null,
      category: null,
      name: null,
      urgency: null,
      desiredResolutionDate: null,
      description: null,
      comment: null,
      attachments: [],
    };
  }

  toTickets() {
    history.push("/tickets/");
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  onHandleChangeAttachment(e) {
    let files = e.target.files;
    for (let i = 0; i < files.length; i++) {
      let reader = new FileReader();
      reader.onloadend = () => {
        this.setState({
          attachments: [
            ...this.state.attachments,
            {
              name: files[i].name,
              blob: new Blob([reader.result], { type: files[i].type }),
            },
          ],
        });
      };
      reader.readAsArrayBuffer(files[i]);
    }console.log(this.state.attachments)
  }

  newTicket() {
    this.createTicket("NEW");
    history.push("/tickets/");
  }
  draftTicket() {
    this.createTicket("DRAFT");
    history.push("/tickets/");
  }
  createTicket(status) {
    var ticket = {};
    ticket.state = status;
    ticket.category = this.state.category;
    ticket.name = this.state.name;
    ticket.urgency = this.state.urgency;
    ticket.desiredResolutionDate = this.state.desiredResolutionDate;
    ticket.description = this.state.description;
    ticket.comment = {
      text: this.state.comment,
      user: JSON.parse(localStorage.User),
    };
    var formData = new FormData();
    formData.append("ticketDto", JSON.stringify(ticket));
    for (let i of this.state.attachments) {
      formData.append("files", i.blob, i.name);
    }
    console.log(formData)
    console.log(ticket)
    axios.post(
      "http://localhost:8099/HelpDesk/tickets",
      formData,
      JSON.parse(localStorage.AuthHeader)
    );
  }

  onDeleteFile(name) {
    let array = this.state.attachments;
    array.splice(array.indexOf(name) - 1, 1);
    this.setState({
      attachments: array,
    });
  }

  render() {
    return (
      <CreateTicketView
        attachments={this.state.attachments}
        toTickets={this.toTickets}
        onHandleChange={this.onHandleChange}
        draftTicket={this.draftTicket}
        newTicket={this.newTicket}
        onHandleChangeAttachment={this.onHandleChangeAttachment}
        onDeleteFile={this.onDeleteFile}
      ></CreateTicketView>
    );
  }
}

export default CreateTicketContainer;
