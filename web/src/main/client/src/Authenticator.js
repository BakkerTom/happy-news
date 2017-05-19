const base64 = require('base-64');

let instance = null;

class Authenticator {

  constructor() {
    if (!instance){
      instance = this;
    }

    return instance;
  }

  authenticate(){
    //Fetches the authentication token from the oauth api
    fetch('/oauth/token', {
      method: 'post',
      headers:{
        "Authorization": "Basic " + base64.encode('happynews-editor:OuNNQtRGBIfUTG2IDICCdOUt'),
        "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
      },
      body: 'grant_type=password&username=admin&password=password'
    })
    .then(blob => blob.json())
    .then(data => {
      //Save acces token to sessionStorage
      sessionStorage.setItem("access_token", data.access_token);
      this.isAuthenticated = true;

      console.log("Retrieved Access Token: " + this.getAccessToken());
    });
  }

  isAuthenticated(){
    const access_token = sessionStorage.access_token;

    if (access_token === null) return false;
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
