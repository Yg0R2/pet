import {MemoryRouter} from 'react-router';

import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import * as serviceWorker from './serviceWorker';

import './index.css';

const app = (
  <React.StrictMode>
    <MemoryRouter>
      <App />
    </MemoryRouter>
  </React.StrictMode>
);

ReactDOM.render(app, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
