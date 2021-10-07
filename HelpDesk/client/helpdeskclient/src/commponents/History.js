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
            <tr key={history.id}>
              <td> {history.date!==null?history.date[0]+"/" +history.date[1]+"/"+history.date[2]:""}</td>
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
