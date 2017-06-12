import React, { Component } from 'react';
import Toggle from '../../components/toggle/Toggle';
import TweetEmbed from 'react-tweet-embed';

import './Tweet.css';

class Tweet extends Component {
  constructor(props) {
    super(props);

    this.state = {
      hidden: props.data.hidden
    };
  }

  handleHide(isHidden) {
    this.setState({
      hidden: isHidden
    });
  }

  getIDFromUrl(url){
    return url.substring(url.lastIndexOf('/') + 1);
  }

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <div className={'flex ' + (this.state.hidden ? 'removed' : '')}>
          <div className='content'>
            <span className="source source-tweet">Tweet</span>
            <TweetEmbed id={this.getIDFromUrl(data.url)} options={{cards: 'hidden', width:550}}/>
          </div>
          <Toggle hidden={ this.state.hidden } uuid={ data.uuid } parentHide={this.handleHide.bind(this)} />
        </div>
      </li>
    );
  }
}

export default Tweet;
