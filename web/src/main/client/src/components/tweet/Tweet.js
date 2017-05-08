import React, { Component } from 'react';

class Tweet extends Component {
  onComponentDidMount() {

  }

  render() {
    return (
      <li>Tweet: { this.props.data.contentText }</li>
    );
  }
}

export default Tweet;
