import React, { Component } from 'react';
import './Article.css';

class Article extends Component {
  deletePost(){
    console.log('Deleting');
  }

  render() {
    const data = this.props.data;
    return (
      <li className='list-group-item flex'>
        <div className='flex'>
          <div className='thumb'>
            <img src={data.imageUrls[0]}/>
          </div>
          <div className='content'>
            <span className='source source-article'>{ data.source }</span>
            <a href={data.url}>
              <h4 className='list-group-item-heading'>{ data.title }</h4>
            </a>
            <p className='list-group-item-text'>{ data.contentText }</p>
          </div>
          <button className='btn btn-default options'>
            <span className="glyphicon glyphicon-trash" aria-hidden="true" onClick={this.deletePost}></span>
          </button>
        </div>
      </li>
    );
  }
}

export default Article;
