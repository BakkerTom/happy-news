import React, { Component } from 'react';
import Toggle from '../../components/toggle/Toggle';
import Flags from '../../components/flags/Flags';

import './Article.css';

class Article extends Component {
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
      <li className='list-group-item flex'>
        <div className={'flex ' + (this.state.hidden ? 'removed' : '')}>
          <div className='thumb'>
            <img src={data.imageUrls[0]} alt='thumbnail'/>
          </div>
          <div className='content'>
            <span className='source source-article'>{ data.sourceName }</span>
            <a href={data.url}>
              <h4 className='list-group-item-heading'>{ data.title }</h4>
            </a>
            <p className='list-group-item-text'>{ data.contentText }</p>
          </div>
          <Toggle hidden={ this.state.hidden } uuid={ data.uuid } parentHide={this.handleHide.bind(this)} />
        </div>

        { flags() }
      </li>
    );
  }
}

export default Article;
