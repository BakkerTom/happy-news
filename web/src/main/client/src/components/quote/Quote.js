import React, { Component } from 'react';
import './Quote.css';

class Quote extends Component {
  onComponentDidMount() {

  }

  render() {
    const data = this.props.data;

    return (
      <li className='list-group-item'>
        <div className='flex'>
          <div className='content'>
            <span className='source source-quote'>{ data.source }</span>
            <blockquote cite={ data.url}>
              <p>{ data.contentText }</p>
              <cite>– { data.author }</cite>
            </blockquote>
          </div>
          <button className='btn btn-default options'>
            <span className="glyphicon glyphicon-trash" aria-hidden="true"></span>
          </button>
        </div>
      </li>
    );
  }
}

export default Quote;
