import React, { Component } from 'react';
import Toggle from '../../components/toggle/Toggle';
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

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <div className={'flex ' + (this.state.hidden ? 'removed' : '')}>
          <div className='content'>
            <span className="source source-tweet">{data.author}</span>

            <p>{data.contentText}</p>
          </div>
          <Toggle hidden={ this.state.hidden } uuid={ data.uuid } parentHide={this.handleHide.bind(this)} />
        </div>
      </li>
    );
  }
}

export default Tweet;
