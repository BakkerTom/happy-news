import React, { Component } from 'react';
import './Tweet.css';

class Tweet extends Component {
  componentDidMount() {
  }

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <div className='flex'>
          <div className='content'>
            <span className="source source-tweet">{data.author}</span>

            <p>{data.contentText}</p>
          </div>
          <button className='btn btn-default options'>
            <span className="glyphicon glyphicon-trash" aria-hidden="true"></span>
          </button>
        </div>
      </li>
    );
  }
}

export default Tweet;
