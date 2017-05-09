import React, { Component } from 'react';

class Tweet extends Component {
  componentDidMount() {
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
