import React from 'react';

import styles from './PetRow.module.css';

const petRow = (props) => {
  return (
    <tr className={props.isOdd ? styles.odd : ""}>
      <td>{props.id}</td>
      <td>{props.title}</td>
      <td className={styles.delete} onClick={() => props.deletePetHandler(props.id)}>Delete</td>
    </tr>

  );
}

export default petRow;
