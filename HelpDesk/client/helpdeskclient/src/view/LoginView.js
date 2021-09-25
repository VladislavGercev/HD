import React from "react";

class LoginView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    const { onHandleChange, onAuth } = this.props;
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-12 my-5"></div>
          <div className="col-md-12 my-5"></div>
        </div>
        <div className="row">
          <div className="col-md-3"></div>
          <div className="col-md-6">
            <h1>Login to the Help Desk</h1>

            <div className="container">
              <div className="row">
                <div className="col-md-2 my-5">
                  <span>Login:</span>
                </div>
                <div className="col-md-8 my-5">
                  <input
                    class="form-control input-lg"
                    name="email"
                    type="email"
                    onChange={onHandleChange}
                  ></input>
                </div>
              </div>
              <div className="row">
                <div className="col-md-2 ">
                  <span className="text-left">Password:</span>
                </div>
                <div className="col-md-8 ">
                  <input
                    class="form-control input-lg"
                    name="password"
                    type="password"
                    onChange={onHandleChange}
                  ></input>
                </div>
              </div>
            </div>
            <div className="container">
              <div className="row">
                <div className="col-md-7"></div>
            <div className="col-md-2 my-4">
              <input
                type="button"
                class="btn btn-block btn-lg btn-primary"
                onClick={onAuth}
                value="Login"
              />
            </div>
            </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default LoginView;
