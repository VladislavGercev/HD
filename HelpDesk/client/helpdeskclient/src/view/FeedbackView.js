import React, { Component } from "react";

class FeedbackView extends Component {
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
        <div className="container w-50 my-5">
          <div className="mb-5">
            <span className="h3">
              Ticket ({this.props.feedback?.ticketDTO?.id}) - {this.props.feedback?.ticketDTO?.name}
            </span>
          </div>
          <div className="mb-6">
            <span className="h5">{this.props.textForm}</span>
          </div>
          <div className="mb-5 my-5" >
            <div className="rating-area">
            <div className="rating-result">
            	<span className={this.props.feedback?.rate > 0?"active":""}></span>	
            	<span className={this.props.feedback?.rate > 1?"active":""}></span>    
            	<span className={this.props.feedback?.rate > 2?"active":""}></span>  
            	<span className={this.props.feedback?.rate > 3?"active":""}></span>    
            	<span className={this.props.feedback?.rate > 4?"active":""}></span>
            </div>
            </div>
          </div>
          <div class="mb-3">
            <textarea
              name="text"
              class="col-6 bg-light border-0 mr-4 mt-3"
              rows="4"
              value={this.props.feedback?.text}
              onChange={onHandleChange}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default FeedbackView;