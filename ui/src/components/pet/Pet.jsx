import React from 'react';

import PetRow from './PetRow';

import apiAxios from '../../apiAxios';

import styles from './Pet.module.css';

class Pet extends React.Component {

  state = {
    petList: [],
    status: {
      message: null,
      type: null
    }
  };

  componentDidMount() {
    this.refreshPetList();
  }

  deletePetHandler = (id) => {
    apiAxios.delete(`/api/pet/${id}`, {data: null})
      .then(_ => {
        this.setState({
          status: {
            message: "Deleted successfully",
            type: styles.success
          }
        });

        this.refreshPetList();
      })
      .catch(_ => this.setState({
        status: {
          message: "Failed to delete",
          type: styles.error
        }
      }))
  }

  refreshPetList = () => {
    apiAxios.get("/api/pet", {data: null})
      .then(response => this.setState({petList: response.data}))
      .catch(error => console.log(error));
  }

  render() {
    const status = this.state.status.message ?
      <div className={this.state.status.type}>{this.state.status.message}</div> :
      null;

    const rows = this.state.petList
      .map((pet, index) =>
        <PetRow
          key={pet.id}
          id={pet.id}
          isOdd={index % 2 === 0}
          title={pet.title}
          deletePetHandler={this.deletePetHandler}
        />
      );

    return (
      <div className={styles.petListWrapper}>
        <h1>Pet list</h1>
        {status}
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
            </tr>
          </thead>
          <tbody>
            {rows}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Pet;
