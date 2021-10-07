import React from "react";
import axios from "axios";
import TicketView from "../view/TicketView";
import history from "../history";
import { saveAs } from "file-saver";
class TicketContainer extends React.Component {
  constructor(props) {
    super(props);
    this.changeTable = this.changeTable.bind(this);
    this.toEdit = this.toEdit.bind(this);
    this.toFeedback = this.toFeedback.bind(this);
    this.toFeedbackNew = this.toFeedbackNew.bind(this);
    this.toTickets = this.toTickets.bind(this);
    this.onHandleChange = this.onHandleChange.bind(this);
    this.addComment = this.addComment.bind(this);
    this.onDownLoad = this.onDownLoad.bind(this);

    this.state = {
      ticketId: this.props.match.params.id,
      user: JSON.parse(localStorage.User),
      attachments: [],
      comments: [],
      ticket: [],
      feedback: null,
    };
  }

  changeTable() {
    if (this.state.table === "history") {
      this.setState({ table: "commenet" });
    } else {
      this.setState({ table: "history" });
    }
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  addComment() {
    axios
      .post(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/comments/",
        { text: this.state.comment, ticketId: this.props.match.params.id },
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ comments: [...this.state.comments, response.data] });
      });
  }
  componentDidMount() {
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" + this.props.match.params.id,
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ ticket: response.data });
      });
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/histories/",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ histories: response.data });
      });
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/comments/",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ comments: response.data });
      });
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/attachments",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        this.setState({ attachments: resp.data });
      });
    axios
      .get(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/feedback",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((resp) => {
        if (resp.data.id != undefined) {
          this.setState({ feedback: resp.data });
        }
      });
  }

  onDownLoad(e) {
    var id = e.target.id;
    var name = e.target.name;
    let url =
      "http://localhost:8099/HelpDesk/tickets/" +
      this.props.match.params.id +
      "/attachments/" +
      id;
    fetch(url, JSON.parse(localStorage.AuthHeader)).then((responce) => {
      if (responce.ok) {
        responce.blob().then((blob) => {
          saveAs(blob, name);
        });
      }
    });
  }

  toTickets() {
    history.push("/tickets");
  }

  toFeedback() {
    history.push("/tickets/" + this.props.match.params.id + "/feedback/");
  }
  toFeedbackNew() {
    history.push("/tickets/" + this.props.match.params.id + "/feedback/new");
  }

  toEdit() {
    history.push("/tickets/" + this.props.match.params.id + "/edit/", [
      this.state.ticket,
    ]);
  }

  render() {
    const { toTickets, toEdit, toFeedback, toFeedbackNew } = this;
    return (
      <TicketView
        feedback={this.state.feedback}
        user={this.state.user}
        ticket={this?.state?.ticket}
        histories={this.state.histories}
        comments={this?.state?.comments}
        attachments={this.state.attachments}
        toEdit={toEdit}
        toFeedbackNew={toFeedbackNew}
        toFeedback={toFeedback}
        toTickets={toTickets}
        onHandleChange={this.onHandleChange}
        addComment={this.addComment}
        onDownLoad={this.onDownLoad}
      ></TicketView>
    );
  }
}
export default TicketContainer;
