import React from 'react';
import {Redirect} from "react-router";

import PetRow from './PetRow';

import authService from '../../services/authService';
import axiosService from '../../services/axiosService';

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
    if (authService.isAuthenticated()) {
      this.refreshPetList();
    }
  }

  deletePetHandler = (id) => {
    axiosService.delete(`/api/pet/${id}`)
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
    axiosService.get("/api/pet")
      .then(response => this.setState({petList: response.data}))
      .catch(error => console.log(error));
  }

  render() {
    if (!authService.isAuthenticated()) {
      return <Redirect to="/" />;
    }

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
