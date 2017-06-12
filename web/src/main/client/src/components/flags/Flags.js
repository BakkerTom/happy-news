import React, { Component } from 'react';
import PropTypes from 'prop-types';

import './Flags.css';

class Flags extends Component {
  constructor(props) {
    super(props);

    this.state = {
      collapsed: true
    };

    this.handleClick = this.handleClick.bind(this);

  }

  handleClick() {
    this.setState({
      collapsed: !this.state.collapsed
    });
  }

  render() {
    var index = 0;

    const listItems = this.props.reasons.map(item => {
      index++;
      return (
        <li className='list-group-item' key={index}>{ item }</li>
      );
    });

    return (
      <div className='panel panel-warning flag-panel'>
        <div className='panel-heading'>
          <a className='panel-title' onClick={this.handleClick}>
            <span className='glyphicon glyphicon-flag' aria-hidden='true'></span> Reported {this.props.reasons.length} times
          </a>
        </div>
        <div className={'panel-collapse ' + (this.state.collapsed ? 'collapse' : '')}>
          <ul className='list-group'>
            { listItems }
          </ul>
        </div>
      </div>
    )
  }
}

Flags.PropTypes = {
  reasons: PropTypes.arrayOf(PropTypes.string).isRequired
}

export default Flags;