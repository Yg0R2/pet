import axios from "axios";
import {v4 as uuid, NIL} from 'uuid';

import authService from './authService';

const apiAxios = axios.create({
  // baseURL: 'https://localhost:8443',
  headers: {
    common: {
      'Content-Type': 'application/json',
    }
  }
});

function getHeaders() {
  const user = authService.getCurrentUser();

  const accessToken = user ? user.accessToken : null;
  const sessionId = user ? user.sessionId : null;

  return {
    Authorization: accessToken,
    requestId: uuid(),
    sessionId: sessionId ? sessionId : NIL
  };
}

function handleUnauthorized(error) {
  if (error.response.status === 401) {
    authService.signOut()
      .then(_ => window.location.href = "/sign-in");
  }

  throw error;
}

class AxiosService {

  delete(url) {
    return apiAxios.delete(url, {data: null, headers: getHeaders()})
      .catch(error => handleUnauthorized(error));
  }

  get(url) {
    return apiAxios.get(url, {data: null, headers: getHeaders()})
      .catch(error => handleUnauthorized(error));
  }

  post(url, data) {
    return apiAxios.post(url, data, {headers: getHeaders()})
      .catch(error => handleUnauthorized(error));
  }

}

export default new AxiosService();
