import React from 'react';
import {NavLink} from 'react-router-dom';

import styles from './NavElement.module.css';

const navElement = (props) => {
  return (
    <li className={styles.navElement}>
      <NavLink to={props.to}>{props.title}</NavLink>
    </li>
  );
}

export default navElement;
