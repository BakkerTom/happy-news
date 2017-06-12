import React, { Component } from 'react';
import './FilterBar.css';

class FilterBar extends Component {
  constructor(props) {
    super(props);

    this.state = {
      flaggedContent: true
    }

    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    this.setState({
      flaggedContent: !this.state.flaggedContent
    });

    console.log(`Show flagged Content: ${this.state.flaggedContent}`);
  }

  render(){
    return (
      <div className='filter-bar'>
        <span>Filter:</span>

        <a className={'filter-btn ' + (this.state.flaggedContent ? '' : 'toggled')} onClick={this.handleClick}>Flagged Content</a>
      </div>
    );
  }
}

export default FilterBar;