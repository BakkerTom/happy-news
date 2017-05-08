import React, { Component } from 'react';

class Article extends Component {
  onComponentDidMount() {

  }

  render() {
    return (
      <li>Article: { this.props.data.title }</li>
    );
  }
}

export default Article;
