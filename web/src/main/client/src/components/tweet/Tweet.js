import React, { Component } from 'react';
import './Tweet.css';

class Tweet extends Component {
  constructor(props) {
    super(props);

    this.state = {
      hidden: props.data.hidden
    };

    this.hidePost = this.hidePost.bind(this);
    this.unhidePost = this.unhidePost.bind(this);
  }

  hidePost() {
    const url = `/admin/posts/${this.props.data.uuid}/hide`;
    fetch(url, {
      method: 'post',
      headers: {
        'Authorization': 'Bearer ' + localStorage.access_token,
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify({
        'hidden': true
      })
    })
    .then(blob => blob.json())
    .then(data => {
      this.setState({
        hidden: true
      });
    });
  }

  unhidePost() {
    const url = `/admin/posts/${this.props.data.uuid}/hide`;
    fetch(url, {
      method: 'post',
      headers: {
        'Authorization': 'Bearer ' + localStorage.access_token,
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify({
        'hidden': false
      })
    })
    .then(blob => blob.json())
    .then(data => {
      this.setState({
        hidden: false
      });
    });
  }

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <div className={'flex ' + (this.state.hidden ? 'removed' : '')}>
          <div className='content'>
            <span className="source source-tweet">{data.author}</span>

            <p>{data.contentText}</p>
          </div>
          <button className='btn btn-default options' onClick={this.state.hidden ? this.unhidePost : this.hidePost}>
            <span className={'glyphicon ' + (this.state.hidden ? 'glyphicon-eye-open' : 'glyphicon-eye-close')}></span>
          </button>
        </div>
      </li>
    );
  }
}

export default Tweet;
