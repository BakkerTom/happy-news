import React, { Component } from 'react';
import Feed from './components/feed/Feed';

import { Navbar } from 'react-bootstrap';
import Authenticator from './Authenticator';

class App extends Component {
  constructor(props){
    super(props);

    this.state = {
      isAuthenticated: false
    };

    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount(){
    const auth = new Authenticator();

    if (!auth.isAuthenticated()){
      auth.authenticate(() => {
        this.setState({
          isAuthenticated: true
        });
      });
    } else {
      this.setState({
        isAuthenticated: true
      });
    }
  }

  render() {
    if (!this.state.isAuthenticated) return (
      <div className="App">
        <Navbar>
          <Navbar.Header>
            <Navbar.Brand>
              <a href="#">Happy News</a>
            </Navbar.Brand>
          </Navbar.Header>
        </Navbar>

        <div className="container">
          <div>Loading...</div>
        </div>

      </div>
    );

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
