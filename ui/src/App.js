import React from 'react';
import {Route, Switch} from 'react-router-dom';

import ChildComponent from './components/ChildComponent';
import DefaultApp from './components/DefaultApp';

import './components/DefaultApp.css';

function App() {
  return (
    <div>
      <Switch>
        <Route path="/pet" component={ChildComponent} />
        <Route path="/" exact component={DefaultApp} />
      </Switch>
    </div>
  );
}

export default App;
