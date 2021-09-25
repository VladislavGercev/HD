import axios from "axios";
import React, { Component } from "react";
import LoginView from "../view/LoginView";

class Login extends Component {
  constructor(props) {
    super(props);
    this.onRetrieveUserFromState = this.onRetrieveUserFromState.bind(this);
    this.onHandleChange = this.onHandleChange.bind(this);
    this.onAuth = this.onAuth.bind(this);
    this.state = {
      email: "",
      password: "",
    };
  }

  createHeader() {
    let authValue = btoa(this.state.email + ":" + this.state.password);
    return { headers: { Authorization: "Basic " + authValue } };
  }

  onAuth() {
    console.log(this.createHeader() + "hello");
    axios
      .post(
        "http://localhost:8099/HelpDesk/users/me",
        {
          email: this.state.email,
          password: this.state.password,
        },
        this.createHeader()
      )
      .then((resp) => {
        localStorage.setItem("AuthHeader", JSON.stringify(this.createHeader()));
        localStorage.setItem("User", JSON.stringify(resp.data));
        window.location.href = "/tickets";
      });
  }

  onRetrieveUserFromState() {
    return {
      email: this.state.email,
      password: this.state.password,
    };
  }

  onHandleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  render() {
    return (
      <LoginView onHandleChange={this.onHandleChange} onAuth={this.onAuth} />
    );
  }
}

export default Login;
