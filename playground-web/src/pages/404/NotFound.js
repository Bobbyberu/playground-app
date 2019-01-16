import React from 'react';

export default class NotFound extends React.Component {

    render() {
        if (this.props.message) {
            return (<div className="not-found">{this.props.message}</div>);
        }
        return (<div className="not-found">Could not find {this.props.path}!</div>);
    }
}