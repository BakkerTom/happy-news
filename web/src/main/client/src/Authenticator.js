const base64 = require('base-64');
import Config from './Config';

let instance = null;

class Authenticator {

  constructor() {
    if (!instance){
      instance = this;
    }

    return instance;
  }

  authenticate(username, password, callback){
    const baseUrl = Config.get('url');
    const url = `${baseUrl}/oauth/token`;
    const oauthId = Config.get('oauthId');
    const oauthSecret = Config.get('oauthSecret');

    //Fetches the authentication token from the oauth api
    fetch(url, {
      method: 'post',
      headers:{
        "Authorization": "Basic " + base64.encode(`${oauthId}:${oauthSecret}`),
        "Origin": window.location.hostname,
        "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
      },
      body: `grant_type=password&username=${username}&password=${password}`
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }

      return response.json();
    })
    .then(data => {
      //Save acces token to sessionStorage
      sessionStorage.setItem("access_token", data.access_token);
      callback(true);
    }, (error) => {
      console.error(error);
      callback(false);
    });
  }

  isAuthenticated() {
    const access_token = sessionStorage.access_token;

    if (access_token === undefined) return false;
    return true;
  }

  getAccessToken(){
    //When authenticated return the access_token
    if (this.isAuthenticated()) {
      return sessionStorage.access_token;
    }

    throw new Error("Needs to authenticate first");
  }
}

export default Authenticator;
