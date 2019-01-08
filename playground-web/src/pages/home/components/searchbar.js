import React from 'react';
import Autosuggest from 'react-autosuggest';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import PlaygroundAPI from '../../../services/playground-api';
import './searchbar.css'

const api = new PlaygroundAPI();

function renderInputComponent(inputProps) {
    return (
        <div id="searchbar">
            <TextField {...inputProps} fullWidth />
        </div>
    );
}

function getSuggestions(searchTerm) {
    return api.getRandomPlaygrounds('test');
}

function getSuggestionValue(suggestion) {
    console.log(suggestion);
    return suggestion.name;
}

export default class Searchbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            playgrounds_suggested: [],
            value: ''
        }
        this.renderSuggestion = this.renderSuggestion.bind(this);
    }

    renderSuggestion(suggestion) {
        return (<MenuItem key={suggestion.id}>{suggestion.name}</MenuItem>);
    }

    handleSuggestionsFetchRequested = ({ value }) => {
        getSuggestions(value)
            .then((response) => {
                this.setState({
                    playgrounds_suggested: response.slice(0, 5)
                });
            });
    };

    handleSuggestionsClearRequested = () => {
        this.setState({
            playgrounds_suggested: [],
        });
    };

    onChange = (event, { newValue, method }) => {
        this.setState({
            value: newValue
        });
    };

    render() {
        const { playgrounds_suggested, value } = this.state;
        const inputProps = {
            placeholder: "Search playground...",
            value,
            onChange: this.onChange
        };

        return (
            <div>
                <h1>######### TEST AUTOSUGGEST #########</h1>
                <Autosuggest
                    suggestions={playgrounds_suggested}
                    renderInputComponent={renderInputComponent}
                    renderSuggestion={this.renderSuggestion}
                    suggestions={this.state.playgrounds_suggested}
                    onSuggestionsFetchRequested={this.handleSuggestionsFetchRequested}
                    onSuggestionsClearRequested={this.handleSuggestionsClearRequested}
                    inputProps={inputProps}
                    getSuggestionValue={getSuggestionValue} />
            </div>
        );
    }
}