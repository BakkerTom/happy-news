import React, { Component } from 'react';

class Tweet extends Component {
  onComponentDidMount() {

  }

  render() {
    return (
      <li className='list-group-item'>
        Tweet: { this.props.data.contentText }
      </li>
    );
  }
}

export default Tweet;
