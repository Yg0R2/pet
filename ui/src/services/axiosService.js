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

class AxiosService {

  delete(url) {
    return apiAxios.delete(url, {data: null, headers: getHeaders()});
  }

  get(url) {
    return apiAxios.get(url, {data: null, headers: getHeaders()});
  }

  post(url, data) {
    return apiAxios.post(url, data, {headers: getHeaders()});
  }

}

export default new AxiosService();
