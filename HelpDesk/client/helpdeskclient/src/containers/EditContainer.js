import React from "react";
import EditView from "../view/EditView";
import axios from "axios";
import history from "../history";

class EditContainer extends React.Component {
  constructor(props) {
    super(props);
    this.toTicketOverview = this.toTicketOverview.bind(this);
    this.onHandleChange = this.onHandleChange.bind(this);
    this.updateTicket = this.updateTicket.bind(this);
    this.newTicket = this.newTicket.bind(this);
    this.draftTicket = this.draftTicket.bind(this);
    this.onDeleteFile = this.onDeleteFile.bind(this);
    this.onHandleChangeAttachment = this.onHandleChangeAttachment.bind(this);
    this.addComment = this.addComment.bind(this);
    this.deleteAttachment = this.deleteAttachment.bind(this);
    this.addAttachment = this.addAttachment.bind(this);
    this.onValid = this.onValid.bind(this)
    this.state = {
      ticketId: this.props.match.params.id,
      state: null,
      category: "Application & Services",
      name: null,
      urgency: "LOW",
      desiredResolutionDate: null,
      description: null,
      comment: null,
      attachments: [],
      newAttachments: [],
      deleteAttachment: [],
    };
  }

  toTicketOverview() {
    window.location.href = "/tickets/" + this.state.ticketId + "/";
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
          newAttachments: [
            ...this.state.newAttachments,
            {
              name: files[i].name,
              blob: new Blob([reader.result], { type: files[i].type }),
            },
          ],
        });
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

  componentDidMount() {
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.state.ticketId +
          "/attachments",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        this.setState({ attachments: resp.data });
      });
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" + this.state.ticketId,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        this.setState({ ticket: resp.data });
      });
  }

  onDeleteFile(id) {
    this.setState({ deleteAttachment: [...this.state.deleteAttachment, id] });
    let array = this.state.attachments;
    array.splice(array.indexOf(id) - 1, 1);
    this.setState({
      attachments: array,
    });
  }

  newTicket() {
    this.updateTicket("NEW");
    history.push("/tickets/");
  }

  draftTicket() {
    this.updateTicket("DRAFT");
    history.push("/tickets/");
  }

  updateTicket(status) {
    var ticket = {};
    ticket.id = this.state.ticketId;
    ticket.state = status;
    ticket.category = this.state.category;
    ticket.name = this.state.name;
    ticket.urgency = this.state.urgency;
    ticket.desiredResolutionDate = this.state.desiredResolutionDate;
    ticket.description = this.state.description;
    ticket.owner = this.state.ticket.owner;
    ticket.createdOn = this.state.ticket.createdOn;
    axios
      .put(
        "http://localhost:8099/HelpDesk/tickets/" + this.state.ticketId,
        ticket,
        JSON.parse(localStorage.AuthHeader)
      )
      .then(() => {
        this.addComment();
        this.deleteAttachment();
        this.addAttachment();
      });
  }

  addComment() {
    if (this.state.comment != null) {
      axios.post(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.state.ticketId +
          "/comments",
        {
          text: this.state.comment,
          ticketId: this.state.ticketId,
        },
        JSON.parse(localStorage.AuthHeader)
      );
    }
  }

  deleteAttachment() {
    if (this.state.deleteAttachment != 0) {
      var formData = new FormData();
      for (var id of this.state.deleteAttachment) {
        axios.delete(
          "http://localhost:8099/HelpDesk/tickets/" +
            this.state.ticketId +
            "/attachments/" +
            id,
          formData,
          JSON.parse(localStorage.AuthHeader)
        );
      }
    }
  }
  addAttachment() {
    if (this.state.newAttachments != null) {
      var formData = new FormData();
      for (let i of this.state.newAttachments) {
        formData.append("files", i.blob, i.name);
      }
      axios.post(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.state.ticketId +
          "/attachments",
        formData,
        JSON.parse(localStorage.AuthHeader)
      );
    }
  }
  

  render() {
    return (
      <EditView
        ticket={this.state.ticket}
        attachments={this.state.attachments}
        ticketId={this.state.ticketId}
        toTicketOverview={this.toTicketOverview}
        onHandleChange={this.onHandleChange}
        onHandleChangeAttachment={this.onHandleChangeAttachment}
        onDeleteFile={this.onDeleteFile}
        draftTicket={this.draftTicket}
        newTicket={this.newTicket}
      ></EditView>
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

export default EditContainer;
