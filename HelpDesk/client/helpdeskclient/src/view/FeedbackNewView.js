import React, { Component } from "react";

class FeedbackNewView extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    let onHandleChange = this.props.onHandleChange;
    return (
      <div>
        <div class="float-left  my-5 mx-5">
          <input
            type="button"
            className="btn btn-success w-15"
            value="Back"
            onClick={() => window.history.back()}
          />
        </div>
        <div class="container w-50 my-5">
          <div class="mb-5">
            <span class="h3">
              Ticket ({this.props.id}) - {this.props.name}
            </span>
          </div>
          <div class="mb-4">
            <span class="h5">{this.props.textForm}</span>
          </div>
          <div class="mb-6 my-5">
            <div class="rating-area">
              <input
                type="radio"
                id="star-5"
                name="rate"
                value="5"
                onChange={this.props.onHandleChange}
              />
              <label for="star-5" title="Оценка «5»"></label>
              <input
                type="radio"
                id="star-4"
                name="rate"
                value="4"
                onChange={this.props.onHandleChange}
              />
              <label for="star-4" title="Оценка «4»"></label>
              <input
                defaultChecked
                type="radio"
                id="star-3"
                name="rate"
                value="3"
                onChange={this.props.onHandleChange}
              />
              <label for="star-3" title="Оценка «3»"></label>
              <input
                type="radio"
                id="star-2"
                name="rate"
                value="2"
                onChange={this.props.onHandleChange}
              />
              <label for="star-2" title="Оценка «2»"></label>
              <input
                type="radio"
                id="star-1"
                name="rate"
                value="1"
                onChange={this.props.onHandleChange}
              />
              <label for="star-1" title="Оценка «1»"></label>
            </div>
          </div>
          <div class="mb-3">
            <textarea
              name="text"
              class="col-6 bg-light border-0 mr-4 mt-3"
              rows="4"
              value={this.props.text}
              onChange={onHandleChange}
            />
          </div>
          <div class="w-50 float-right mr-5">
            <input
              className="btn btn-success w-25"
              type="button"
              value="Submit"
              onClick={this.props.onAddFeedback}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default FeedbackNewView;
