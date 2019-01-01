import React, {Component} from 'react';
import './playground-new.css';

export default class PlaygroundNew extends Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        alert('You added the playground ' + this.state.value);
        event.preventDefault();
    }

    render() {
        return (
            /* TODO
             Formulaire avec :
             - Img
             - Nom terrain
             - Adresse
             - Liste sport
             - Chbx privé/couvert
             - Description
            */

            <form onSubmit={this.handleSubmit}>
                <label>
                    Nom du terrain:
                    <input type="text" value={this.state.value} onChange={this.handleChange} />
                </label>
                <input type="submit" value="Submit" />
            </form>
        )
    }
}