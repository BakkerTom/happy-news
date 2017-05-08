import React, { Component } from 'react';
import { Grid, Row, Col, Image } from 'react-bootstrap';
import './Article.css';

class Article extends Component {
  onComponentDidMount() {

  }

  render() {
    const data = this.props.data;
    return (
      <li className='list-group-item flex'>
        <div className='flex'>
          <div className='thumb'>
            <img src={data.imageUrls[0]}/>
          </div>
          <div>
            <span className='source'>{ data.source }</span>
            <a href={data.url}>
              <h4 className='list-group-item-heading'>{ data.title }</h4>
            </a>
            <p className='list-group-item-text'>{ data.contentText }</p>
          </div>
        </div>
      </li>
    );
  }
}

export default Article;
