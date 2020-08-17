import axiosService from "./axiosService";

const ITEM_KEY = 'user';

class AuthService {

  getCurrentUser() {
    return JSON.parse(localStorage.getItem(ITEM_KEY));
  }

  isAuthenticated() {
    return localStorage.getItem(ITEM_KEY) != null;
  }

  signIn(userName, password) {
    const data = {
      userName: userName,
      password: password
    };

    return axiosService.post("/api/sign-in", data)
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem(ITEM_KEY, JSON.stringify(response.data));
        }
      });
  }

  signOut() {
    return axiosService.post("/api/sign-out", {})
      .catch(error => console.log(error))
      .finally(_ => {
        localStorage.removeItem(ITEM_KEY);
      });
  }

}

export default new AuthService();
