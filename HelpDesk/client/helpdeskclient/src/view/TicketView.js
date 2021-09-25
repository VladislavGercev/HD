import React from "react";
import Comment from "../commponents/Comment";
import History from "../commponents/History";
import TicketInfo from "../commponents/TicketInfo";

class TicketView extends React.Component {
  constructor(props) {
    super(props);
    this.changeTable = this.changeTable.bind(this);
    this.viewComment = this.viewComment.bind(this);
    this.viewHistory = this.viewHistory.bind(this);
    this.state = {
      view: "history",
      buttonMy: "btn btn-light w-100 m-4",
      buttonAll: "btn btn-primary w-100 m-4",
    };
  }

  changeTable() {
    if (this.state.view === "history") {
      return <History histories={this.props.histories}></History>;
    } else {
      return (
        <Comment
          addComment={this.props.addComment}
          comments={this?.props?.comments}
          onHandleChange={this.props.onHandleChange}
        ></Comment>
      );
    }
  }

  viewHistory() {
    this.setState({ view: "history" });
    this.setState({ buttonMy: "btn btn-light w-100 m-4" });
    this.setState({ buttonAll: "btn btn-primary w-100 m-4" });
  }
  viewComment() {
    this.setState({ view: "comment" });
    this.setState({ buttonMy: "btn btn-primary w-100 m-4" });
    this.setState({ buttonAll: "btn btn-light w-100 m-4" });
  }

  render() {
    console.log(this.props.feedback);
    const { toTickets, toEdit, toFeedback, toFeedbackNew } = this.props;
    return (
      <div>
        <div className="container">
          <div className="row">
            <div className="col-md-2 my-5">
              <input
                type="button"
                className="btn btn-success p-2 w-100"
                onClick={toTickets}
                value="Ticket List"
              ></input>
            </div>
            <div className="col-md-8 my-5">
              <TicketInfo
                ticket={this?.props?.ticket}
                attachments={this.props.attachments}
                onDownLoad={this.props.onDownLoad}
              ></TicketInfo>
            </div>
            <div className="col-md-2 my-5">
              {this.props.ticket.state === "DRAFT" &&
                (this.props.user.role === "MANAGER" ||
                  this.props.user.role === "EMPLOYEE") && (
                  <input
                    type="button"
                    className="btn btn-success p-2 w-100"
                    onClick={toEdit}
                    value="Edit"
                  ></input>
                )}
              {this.props.feedback === null &&
                this.props.ticket.state === "NEW" &&
                (this.props.user.role === "MANAGER" ||
                  this.props.user.role === "EMPLOYEE") && (
                  <input
                    type="button"
                    className="btn btn-success p-2 w-100"
                    onClick={toFeedbackNew}
                    value="Leave Fedback"
                  ></input>
                )}
              {this.props.feedback !=null &&
                this.props.ticket.state === "NEW" &&
                (this.props.user.role === "MANAGER" ||
                  this.props.user.role === "EMPLOYEE") && (
                  <input
                    type="button"
                    className="btn btn-success p-2 w-100"
                    onClick={toFeedback}
                    value="Fedback"
                  ></input>
                )}
            </div>
          </div>
        </div>
        <div className="container">
          <div className="row">
            <div className="col-md-6">
              <input
                type="button"
                value="History"
                className={this.state.buttonAll}
                onClick={this.viewHistory}
              ></input>
            </div>
            <div className="col-md-6">
              <input
                type="button"
                value="Comments"
                className={this.state.buttonMy}
                onClick={this.viewComment}
              ></input>
            </div>
          </div>
        </div>
        {this.changeTable()}
      </div>
    );
  }
}

export default TicketView;
