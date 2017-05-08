import React, { Component } from 'react';
import Article from '../../components/article/Article';
import Tweet from '../../components/tweet/Tweet';
import Quote from '../../components/quote/Quote';

class Feed extends Component {

  constructor(props) {
    super(props);

    //Initialize an empty state object
    this.state = {};
  }

  componentDidMount() {
    // Fetch the API endpoint Post
    fetch('/post')
      .then(blob => blob.json()) // Convert Blob to JSON data
      .then(data => {
        //Fill state with post data
        this.setState({
          feedData: data.content,
          pageSize: data.size,
          pageNumber: data.number
        });
      });
  }

  render() {
    //Display a loading text when no data is loaded yet
    if (!this.state.feedData) return <div>Loading...</div>;

    //Map correct components to results
    const posts = this.state.feedData.map(post => {
      switch ( post.source ){
        case 'Twitter':
          return <Tweet data={post} />;
        case 'Inspirational Quote':
          return <Quote data={post} />;
        default:
          return <Article data={post} />;
      }
    });

    return (
      <ul className='list-group'>
        { posts }
      </ul>
    );
  }
}

export default Feed;
