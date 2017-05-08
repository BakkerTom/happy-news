import React, { Component } from 'react';
import Feed from './components/feed/Feed';

class App extends Component {
  render() {
    return (
      <div className="App">
        <h1>Happy News</h1>
        <Feed />
      </div>
    );
  }
}

export default App;
