import React, { Component } from 'react';
import PropTypes from 'prop-types';
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

    this.props.handler("FLAGGED", this.state.flaggedContent);
  }

  render(){
    return (
      <div className='filter-bar'>
        <span>Filter:</span>

        <a className={'filter-btn ' + (this.state.flaggedContent ? '' : 'toggled')} 
          onClick={this.handleClick}>Flagged Content</a>
      </div>
    );
  }
}

FilterBar.propTypes = {
  handler: PropTypes.func.isRequired
}

export default FilterBar;