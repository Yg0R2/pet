import React from 'react';
import {Route, Switch} from 'react-router-dom';

import DefaultApp from './components/DefaultApp';
import NavBar from './components/nav/NavBar';
import Pet from './components/pet/Pet';

import './components/DefaultApp.css';

function App() {
  return (
    <div>
      <NavBar />

      <Switch>
        <Route path="/pet" component={Pet} />
        <Route path="/" exact component={DefaultApp} />
      </Switch>
    </div>
  );
}

export default App;
