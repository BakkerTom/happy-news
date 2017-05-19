import React, { Component } from 'react';
import ReactPaginate from 'react-paginate';

import Article from '../../components/article/Article';
import Tweet from '../../components/tweet/Tweet';
import Quote from '../../components/quote/Quote';
import Authenticator from '../../Authenticator';

let PAGE_SIZE = 20;
let auth = new Authenticator();

class Feed extends Component {

  constructor(props) {
    super(props);

    //Initialize an empty state object
    this.state = {};
  }

  componentDidMount() {
    //Load first page when component mounts
    this.loadItems(0);
  }

  //loadItems load
  loadItems(pageNumber){
    const url = `/admin/posts?page=${pageNumber}&size=${PAGE_SIZE}`;

    //Check if the client is already authenticated
    if ( !auth.isAuthenticated ){
      auth.authenticate();
    }

    fetch(url, {
        headers: {
          'Authorization': 'Bearer ' + auth.getAccessToken(),
          'Content-Type': 'application/json; charset=utf-8'
        }
      })
      .then(blob => blob.json())
      .then(data => {
        this.setState({
          feedData: data.content,
          pageSize: data.size,
          pageNumber: data.number,
          pageCount: data.totalPages
        });
      });
  }

  handlePageClick = (data) => {
    let selected = data.selected;
    this.loadItems(selected);

    //Scroll to top of window
    window.scrollTo(0, 0);
  };

  render() {
    //Display a loading text when no data is loaded yet
    if (!this.state.feedData) return <div>Loading...</div>;

    //Map correct components to results
    const posts = this.state.feedData.map(post => {
      switch ( post.type ){
        case 'tweet':
          return <Tweet data={post} key={post.uuid}/>;
        case 'quote':
          return <Quote data={post} key={post.uuid}/>;
        case 'article':
          return <Article data={post} key={post.uuid} />;
        default:
          return <li className='list-group-item'>Unsupported content</li>;
      }
    });

    return (
      <div>
      <ul className='list-group'>
        { posts }
      </ul>

      <ReactPaginate previousLabel={"previous"}
                       nextLabel={"next"}
                       breakLabel={<a href="">...</a>}
                       breakClassName={"break-me"}
                       pageCount={this.state.pageCount}
                       marginPagesDisplayed={2}
                       pageRangeDisplayed={5}
                       onPageChange={this.handlePageClick}
                       containerClassName={"pagination"}
                       subContainerClassName={"pages pagination"}
                       activeClassName={"active"} />
      </div>
    );
  }
}

export default Feed;
