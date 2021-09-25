import React from "react";

class History extends React.Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  render() {
    return (

      <div className="container">
        <table className="table table-hover table-bordered">
          <thead>
            <tr className="table-active" >
              <th>Date</th>
              <th>User</th>
              <th>Action</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
          {this?.props?.histories?.map((history) => (
            <tr>
              <td>{history.date.dayOfMonth  + '/' + history.date.dayOfMonth + '/' + history.date.year}</td>
              <td>{history.user.firstName +"  "+ history.user.lastName}</td>
              <td>{history.action}</td>
              <td>{history.description}</td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    )


  }

}

export default History
