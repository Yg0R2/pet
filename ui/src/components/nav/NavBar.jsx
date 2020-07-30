import React from 'react';

import NavElement from './NavElement';

import styles from './NavBar.module.css';

class NavBar extends React.Component {
  render() {
    return (
      <ul className={styles.navBar}>
        <NavElement to="/" title="Home" />
        <NavElement to="/pet" title="PET" />
      </ul>
    );
  }
}

export default NavBar;
