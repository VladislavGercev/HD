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
    this.state = {
      ticketId: this.props.match.params.id,
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

  toTicketOverview() {
    window.location.href = "/tickets/" + this.state.ticketId + "/";
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  createParams(status) {
    return {
      category: this.state.category,
      name: this.state.name,
      description: this.state.description,
      urgency: this.state.urgency,
      desiredResolutionRdate: this.state.desiredResolutionDate,
      state: status,
      attachments: this.state.attachments,
      comment: this.state.comments,
      ticket: { name: "" },
    };
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
    console.log(this.state.attachments);
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

  onDeleteFile(name) {
    let array = this.state.attachments;
    array.splice(array.indexOf(name) - 1, 1);
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
    // ticket.comment = {
    //   text: this.state.comment,
    //   user: JSON.parse(localStorage.User),
    // };
    var formData = new FormData();
    formData.append("ticketDto", JSON.stringify(ticket));
    axios
      .put(
        "http://localhost:8099/HelpDesk/tickets/" + this.state.ticketId,
        ticket,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((responce) => {
        if (this.state.comment) {
          axios
            .post(
              "http://localhost:8099/HelpDesk/tickets/" +
                this.state.ticketId +
                "/comments",
              {
                text: this.state.comment,
                ticketId: this.state.ticketId,
              },
              JSON.parse(localStorage.AuthHeader)
            )
            .then((responce) => {});
        }
        if (this.state.attachments.length !== 0) {
          var formData = new FormData();
          for (let i of this.state.attachments) {
            formData.append("files", i.blob, i.name);
          }
          axios
            .post(
              "http://localhost:8099/HelpDesk/tickets/" +
              this.state.ticketId +
                "/attachments",
              formData,
              JSON.parse(localStorage.AuthHeader)
            )
            .then((responce) => {});
        }

        history.push("/tickets");
      })
      .catch((error) => {});
  }

  // updateTicket(status) {
  //   var ticket = {};
  //   ticket.state = status;
  //   ticket.category = this.state.category;
  //   ticket.name = this.state.name;
  //   ticket.urgency = this.state.urgency;
  //   ticket.desiredResolutionDate = this.state.desiredResolutionDate;
  //   ticket.description = this.state.description;
  //   ticket.comment = {
  //     text: this.state.comment,
  //     user: JSON.parse(localStorage.User),
  //   };
  //   var formData = new FormData();
  //   formData.append("ticketDto", JSON.stringify(ticket));
  //   for (let i of this.state.attachments) {
  //     formData.append("files", i.blob, i.name);
  //   }
  //   console.log(formData);
  //   console.log(ticket);
  //   axios.put(
  //     "http://localhost:8099/HelpDesk/tickets/" + this.state.ticketId,
  //     formData,
  //     JSON.parse(localStorage.AuthHeader)
  //   );
  // }

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
}

export default EditContainer;
