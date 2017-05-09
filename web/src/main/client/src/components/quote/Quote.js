import React, { Component } from 'react';
import './Quote.css';

class Quote extends Component {
  onComponentDidMount() {

  }

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <span className='source source-quote'>{ data.source }</span>
        <blockquote cite={ data.url}>
          <p>{ data.contentText }</p>
          <cite>– { data.author }</cite>
        </blockquote>
      </li>
    );
  }
}

export default Quote;
