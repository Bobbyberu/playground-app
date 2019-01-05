import React, { Component } from 'react';
import Searchbar from '../home/components/searchbar';

export default class SignIn extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <h1>SignIn</h1>
                <Searchbar />
            </div>
        )
    }
}