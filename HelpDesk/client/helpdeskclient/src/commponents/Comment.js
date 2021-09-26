import React from "react";

class Comment extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div className="container">
        <table className="table table-hover table-bordered">
          <thead>
            <tr className="table-active">
              <th>Date</th>
              <th>User</th>
              <th>Comment</th>
            </tr>
          </thead>
          <tbody>
            {this?.props?.comments.map((comment) => (
              <tr>
                <td>
                  {comment.date.dayOfMonth +
                    "/" +
                    comment.date.monthValue +
                    "/" +
                    comment.date.year}
                </td>
                <td>
                  {comment?.user?.firstName + "  " + comment?.user?.lastName}
                </td>
                <td>{comment.text}</td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="container ">
          <div className="row">
            <div className="col-md-8">
              <textarea
                name="comment"
                class="col-12 bg-light border-0 mr-4 mt-3"
                rows="4"
                value={this.props.comment}
                onChange={this.props.onHandleChange}
              />
            </div>
            <div className="col-md-2">
              <div class="text-right w-50">
                <input
                  type="button"
                  className="btn btn-secondary my-5 rows=8"
                  value="Add Comment"
                  onClick={this.props.addComment}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Comment;
