import React from 'react';

import authService from "../../services/authService";

class SignIn extends React.Component{

  state = {
    userName: null,
    password: null
  };

  onChangeHandler = (event) => {
    this.setState({[event.target.name]: event.target.value});
  }

  signInHandler = (event) => {
    event.preventDefault();

    if (this.state.password && this.state.userName) {
      authService.signIn(this.state.userName, this.state.password)
        .then(() => {
          this.props.history.push("/pet");

          window.location.reload();
        })
        .catch(error => console.log(error))
    }
  }

  render() {
    return (
      <div>
        <h1>Sign in</h1>
        <form>
          <label>User name</label>
          <input name="userName" type="text" onChange={this.onChangeHandler} />

          <label>Password</label>
          <input name="password" type="password" onChange={this.onChangeHandler} />

          <button onClick={this.signInHandler}>Sign In</button>
        </form>
      </div>
    );
  }

}

export default SignIn;
