import React, { Component } from 'react';
import Feed from './components/feed/Feed';
import Login from './components/login/Login';

import { Navbar } from 'react-bootstrap';

class App extends Component {
  constructor(props){
    super(props);

    this.state = {
      isAuthenticated: false
    };

    this.handleAuth = this.handleAuth.bind(this);
    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount() {

    //Check to see if an access_token already exists in the sessionStorage
    if (sessionStorage.access_token != null){
      this.setState({
        isAuthenticated: true
      });
    }
  }

  //Callback if Login component correctly authenticates the user
  handleAuth(){
    this.setState({
      isAuthenticated: true
    });
  }

  render() {
    let content = <Login handleAuth={this.handleAuth} />;

    //If the user is authenticated display the feed
    if (this.state.isAuthenticated) {
      content = <Feed />;
    }

    return (
      <div className="App">
        <Navbar inverse>
          <Navbar.Header>
            <Navbar.Brand>
              <a href="/">Happy News</a>
            </Navbar.Brand>
          </Navbar.Header>
        </Navbar>

        <div className="container">
          { content }
        </div>

      </div>
    );
  }
}

export default App;
