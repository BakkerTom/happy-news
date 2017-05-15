import React, { Component } from 'react';
import Feed from './components/feed/Feed';
const base64 = require('base-64');

import { Navbar } from 'react-bootstrap';

class App extends Component {
  componentDidMount() {
    //Authenticate the current client
    if (localStorage.access_token != null) return (
      console.log('Already retrieved token: ' + localStorage.access_token)
    );

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
      localStorage.setItem("access_token", data.access_token);

      console.log("Retrieved Access Token: " + localStorage.access_token);
    });
  }

  render() {
    return (
      <div className="App">
        <Navbar>
          <Navbar.Header>
            <Navbar.Brand>
              <a href="#">Happy News</a>
            </Navbar.Brand>
          </Navbar.Header>
        </Navbar>

        <div className="container">
          <Feed />
        </div>

      </div>
    );
  }
}

export default App;
