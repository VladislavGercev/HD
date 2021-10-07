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
    this.onDeleteFile = this.onDeleteFile.bind(this);
    this.addAttachment = this.addAttachment.bind(this);
    this.addComment = this.addComment.bind(this);
    this.onValid = this.onValid.bind(this)
    this.state = {
      state: null,
      category: "Application & Services",
      name: null,
      urgency: "LOW",
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
    if (this.onValid(event.target)) {
      this.setState({ [event.target.name]: event.target.value });
    }
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
    }
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
    axios
      .post(
        "http://localhost:8099/HelpDesk/tickets",
        ticket,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        this.addAttachment(resp.data.id);
        this.addComment(resp.data.id);
      });
    history.push("/tickets");
  }

  addAttachment(id) {
    if (this.state.attachments != 0) {
      var formData = new FormData();
      for (let i of this.state.attachments) {
        formData.append("files", i.blob, i.name);
      }
      axios
        .post(
          "http://localhost:8099/HelpDesk/tickets/" +
            id +
            "/attachments",
          formData,
          JSON.parse(localStorage.AuthHeader)
        )
        .then((responce) => {});
      history.push("/tickets");
    }
  }

  addComment(id) {
    if (this.state.comment != null) {
      var comment = {
        text: this.state.comment,
        user: JSON.parse(localStorage.User),
      };
      axios.post(
        "http://localhost:8099/HelpDesk/tickets/" + id + "/comments",
        comment,
        JSON.parse(localStorage.AuthHeader)
      );
    }
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
  onValid(data) {
    let result = false;
    const isName = /^[-\s"'`~.a-z#!$%@^&*+=_,:;?/<>|()[\]{}]{1,100}$/;
    const isDescription = /^[-\s"'`~.a-z#!$%@^&*+=_,:;?/<>|()[\]{}]{1,500}$/i;
    const isText = /^[-\s"'`~.\w#!$%@^&*+=,:;?/<>|()[\]{}]{1,500}$/;
    switch (data.name) {
      case "name":
        result = isName.test(data.value);
        break;
      case "description":
        result = isDescription.test(data.value);
        break;
      case "text":
        result = isText.test(data.value);
        break;
      default:
        result = true;
    }
    return result;
  }
}

export default CreateTicketContainer;
