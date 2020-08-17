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

        return response.data;
      })
  }

  signOut() {
    localStorage.removeItem(ITEM_KEY);

    axiosService.post("/api/sign-out", {})
      .then(_ => {
        window.location.href = "/";
        // window.location.reload();
      })
      .catch(error => console.log(error));
  }

}

export default new AuthService();
