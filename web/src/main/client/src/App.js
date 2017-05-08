import React, { Component } from 'react';
import Feed from './components/feed/Feed';

import { Navbar } from 'react-bootstrap';

class App extends Component {
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
