import React, { Component } from 'react';

class Quote extends Component {
  onComponentDidMount() {

  }

  render() {
    return (
      <li>Tweet: { this.props.data.contentText }</li>
    );
  }
}

export default Quote;
