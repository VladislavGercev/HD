import axios from "axios";
import React, { Component } from "react";
import FeedbackNewView from "../view/FeedbackNewView";
import history from "../history";
import FeedbackView from "../view/FeedbackView";

class FeedbackContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.ticketId,
      name: "",
      rate: "",
      text: "",
      feedback: null,
    };
    this.onHandleChange = this.onHandleChange.bind(this);
    this.onAddFeedback = this.onAddFeedback.bind(this);
    this.onRetrieveFeedback = this.onRetrieveFeedback.bind(this);
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
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
          "/feedback/",
        JSON.parse(localStorage.AuthHeader)
      )
      .then((response) => {
        this.setState({ feedback: response.data });
      });
  }

  onAddFeedback() {
    axios
      .post(
        "http://localhost:8099/HelpDesk/tickets/" +
          this.props.match.params.id +
          "/feedback/",
        { name: this.state.name, text: this.state.text, rate: this.state.rate },
        JSON.parse(localStorage.AuthHeader)
      )
      .then(() => {
        history.push("/tickets/" + this.props.match.params.id + "/");
      });
  }

  onRetrieveFeedback() {
    return {
      rate: this.state.rate,
      text: this.state.text,
    };
  }

  render() {
    if (window.location.pathname.includes("new")) {
      return (
        <div>
          <FeedbackNewView
            id={this.state.id}
            name={this.state.name}
            rate={this.state.rate}
            text={this.state.text}
            textForm="Please, rate your satisfaction with the solution:"
            onHandleChange={this.onHandleChange}
            onAddFeedback={this.onAddFeedback}
            ticket={this.state.ticket}
          />
        </div>
      );
    } else {
      return (
        <div>
          <FeedbackView
            feedback={this.state.feedback}
            textForm="Feedback"
            ticket={this.state.ticket}
          />
        </div>
      );
    }
  }
}

export default FeedbackContainer;
