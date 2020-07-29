import React from 'react';

import authService from '../../services/authService';
import NavElement from './NavElement';

import styles from './NavBar.module.css';

class NavBar extends React.Component {

  render() {
    const isAuthenticated = authService.isAuthenticated();

    return (
      <ul className={styles.navBar}>
        <NavElement to="/" title="Home" />
        {isAuthenticated && (
          <NavElement to="/pet" title="PET" />
        )}
        {!isAuthenticated && (
          <NavElement to="/sign-in" title="Sign In" />
        )}
        {isAuthenticated && (
          <NavElement to="/sign-out" title="Sign Out" />
        )}
      </ul>
    );
  }
}

export default NavBar;
