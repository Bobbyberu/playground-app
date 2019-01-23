import React from 'react';
import Autosuggest from 'react-autosuggest';
import MenuItem from '@material-ui/core/MenuItem';
import PlaygroundAPI from '../../../../services/playground-api';
import './searchbar.css'
import { withStyles } from '@material-ui/core/styles';
import { fade } from '@material-ui/core/styles/colorManipulator';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';

var timeout = null;

// styles
const styles = theme => ({
    search: {
        position: 'relative',
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            marginLeft: theme.spacing.unit,
            width: 'auto',
        },
        borderRadius: 5
    },
    searchIcon: {
        width: theme.spacing.unit * 9,
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    inputRoot: {
        color: 'inherit',
        width: '100%',
    },
    inputInput: {
        paddingTop: theme.spacing.unit,
        paddingRight: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 5,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: 240,
            '&:focus': {
                width: 240,
            },
        },
    }
});

function getSuggestions(searchTerm) {
    return PlaygroundAPI.getSearchResult(searchTerm);
}

function getSuggestionValue(suggestion) {
    return suggestion.name;
}

class Searchbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            playgrounds_suggested: [],
            value: ''
        }
        this.renderInputComponent = this.renderInputComponent.bind(this);
        this.renderSuggestion = this.renderSuggestion.bind(this);
    }

    renderInputComponent(inputProps) {
        const { classes } = this.props;
        return (
            <div className={classes.search}>
                <div className={classes.searchIcon}>
                    <SearchIcon />
                </div>
                <InputBase
                    {...inputProps}
                    placeholder="Search…"
                    classes={{
                        root: classes.inputRoot,
                        input: classes.inputInput,
                    }}
                />
            </div>
        );
    }

    renderSuggestion(suggestion) {
        return (<MenuItem key={suggestion.id}>{suggestion.name}</MenuItem>);
    }

    handleSuggestionsFetchRequested = ({ value }) => {
        clearTimeout(timeout);

        // timeout to wait for user to stop typing so that we won't make too much http calls
        timeout = setTimeout(function () {
            getSuggestions(value)
                .then((response) => {
                    this.setState({
                        playgrounds_suggested: response.slice(0, 5)
                    });
                });
        }.bind(this), 500);
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
            onChange: this.onChange,
        };

        return (
            <div className="search-autosuggest">
                <Autosuggest
                    suggestions={playgrounds_suggested}
                    renderInputComponent={this.renderInputComponent}
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

export default withStyles(styles)(Searchbar);