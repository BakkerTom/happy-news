import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Toggle extends Component {

  constructor(props){
    super(props);

    this.state = {
      hidden: props.hidden
    }

    this.handleHide = this.handleHide.bind(this);
  }

  handleHide() {
    const url = `/admin/posts/${this.props.uuid}/hide`;
    fetch(url, {
      method: 'post',
      headers: {
        'Authorization': 'Bearer ' + sessionStorage.access_token,
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify({
        'hidden': !this.state.hidden
      })
    })
    .then(blob => blob.json())
    .then(data => {
      this.setState({
        hidden: !this.state.hidden
      });

      this.props.parentHide(this.state.hidden);
    });
  }

  render(){
    return (
      <button className='btn btn-default options' onClick={this.handleHide}>
        <span className={'glyphicon ' + (this.state.hidden ? 'glyphicon-eye-open' : 'glyphicon-eye-close')}></span>
      </button>
    );
  }
}

Toggle.propTypes = {
  parentHide: PropTypes.func.isRequired,
  hidden: PropTypes.bool.isRequired,
  uuid: PropTypes.string.isRequired
};

export default Toggle;
