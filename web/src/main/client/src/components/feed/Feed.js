import React, { Component } from 'react';
import ReactPaginate from 'react-paginate';

import Article from '../../components/article/Article';
import Tweet from '../../components/tweet/Tweet';
import Quote from '../../components/quote/Quote';
import FilterBar from '../../components/filterbar/FilterBar';

//Static page_size field
let PAGE_SIZE = 20;

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

  /**
   * load the items via the Happy News API
   * @param {int} pageNumber 
   */
  loadItems(pageNumber){
    const url = `https://happynews-api.svendubbeld.nl/admin/posts?page=${pageNumber}&size=${PAGE_SIZE}`;

    fetch(url, {
        headers: {
          'Authorization': 'Bearer ' + sessionStorage.access_token,
          'Content-Type': 'application/json; charset=utf-8',
          'Origin': window.location.hostname,
          'Access-Control-Request-Method': 'get',
          'Access-Control-Request-Headers': 'X-PINGOTHER, Content-Type'
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

  /**
   * Handles the 
   * @param {string} filter - The filter ID string
   * @param {boolean} state - Wether the filter should be on or off
   */
  filterHandler(filter, state) {
    console.log(`${filter}: ${state}`);
  }

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
      
      <FilterBar handler={this.filterHandler}/>

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
