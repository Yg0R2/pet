import React from 'react';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';

import authService from './services/authService';
import DefaultApp from './components/DefaultApp';
import NavBar from './components/nav/NavBar';
import Pet from './components/pet/Pet';
import SignIn from './components/auth/SignIn';

import './App.css';

class App extends React.Component {

  signOut = () => {
    authService.signOut()
      .then(_ => {
        window.location.href = "/";
        // window.location.reload();
      });
  }

  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <NavBar />

          <Switch>
            <Route exact path="/" component={DefaultApp} />
            <Route path="/pet" component={Pet} />
            <Route path="/sign-in" component={SignIn} />
            <Route path="/sign-out" render={this.signOut} />
            <Redirect to="/" />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }

}

export default App;
