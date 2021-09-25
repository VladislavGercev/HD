import React, { Component } from "react";
import FeedbackForm from "../commponents/FeedbackForm";

class FeedbackNew extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      name: "",
      rate: "",
      text: "",
    };
    this.onHandleChange = this.onHandleChange.bind(this);
    this.onAddFeedback = this.onAddFeedback.bind(this);
    this.onRetrieveFeedback = this.onRetrieveFeedback.bind(this);
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  componentDidMount() {
    this.setState({
      id: this.props.match.params.ticketId,
      name: localStorage.getItem("ticketNameForFeedback"),
      rate: "",
      text: "",
    });
  }

  onAddFeedback() {
    this.props.addFeedback(
      this.props.match.params.ticketId,
      this.onRetrieveFeedback()
    );
  }

  onRetrieveFeedback() {
    return {
      rate: this.state.rate,
      text: this.state.text,
    };
  }

  render() {
    return (
      <div>
        <FeedbackForm
          id={this.state.id}
          name={this.state.name}
          rate={this.state.rate}
          text={this.state.text}
          textForm="Please, rate your satisfaction with the solution:"
          onHandleChange={this.onHandleChange}
        />
        <input onSubmit={this.onAddFeedback} />
      </div>
    );
  }
}

export default FeedbackNew;
