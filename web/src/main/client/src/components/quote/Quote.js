import React, { Component } from 'react';
import Toggle from '../../components/toggle/Toggle';
import Flags from '../../components/flags/Flags';

import './Quote.css';

class Quote extends Component {
  constructor(props) {
    super(props);

    this.state = {
      hidden: props.data.hidden
    };
  }

  handleHide(isHidden) {
    this.setState({
      hidden: isHidden
    });
  }

  render() {
    const data = this.props.data;

    const flags = () => {
        if (data.flagReasons.length > 0) {
          return <Flags reasons={data.flagReasons} />;
        }

        return null;
    };

    return (
      <li className='list-group-item'>
        <div className={'flex ' + (this.state.hidden ? 'removed' : '')}>
          <div className='content'>
            <span className='source source-quote'>{ data.source }</span>
            <blockquote cite={ data.url}>
              <p>{ data.contentText }</p>
              <cite>– { data.author }</cite>
            </blockquote>
          </div>
          <Toggle hidden={ this.state.hidden } uuid={ data.uuid } parentHide={this.handleHide.bind(this)} />
        </div>
        { flags() }
      </li>
    );
  }
}

export default Quote;
