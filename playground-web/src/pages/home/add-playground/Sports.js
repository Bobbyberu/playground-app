import React from 'react';
import PropTypes from 'prop-types';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import PlaygroundAPI from '../../../services/playground-api';
import { connect } from 'react-redux';

// Override de certains éléments de la card 
const theme = createMuiTheme({
    overrides: {
        MuiListItem: {
            gutters: {
                paddingLeft: 0,
            },
        }
    },
});

const styles = theme => ({
    root: {
        width: '100%',
    },
});

class Sports extends React.Component {
    constructor(props) {
        super(props)
        // On garde dans un state les cases cochées
        this.state = {
            checked: this.props.sports,
            sports: []
        }
        this.api = new PlaygroundAPI();
    }

    componentDidMount() {
        // Récupérer la liste des sports après que le composant ait été retranscrit dans le DOM
        this.api.getAllSports()
            .then((response) => {
                this.setState({
                    sports: response
                });
            });
    }

    componentWillUnmount() {
		const action = { type: 'SET_SPORTS', value: this.state.checked };
		this.props.dispatch(action);
	}

    // Methode qui gère le changement d'état sur une checkbox
    handleToggle = value => () => {
        const { checked } = this.state;
        // currentIndex référence la case que l'on coche ou décoche
        const currentIndex = checked.indexOf(value);
        // On initialise un nouveau state à partir du state existant
        const newChecked = [...checked];

        /* 
        Si la checkbox n'est pas cochée, on l'ajoute à la liste des cases cochées et inversement
        La valeur d'une case est de -1 si elle n'est pas cochée
        */
        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        // On met à jour le state avec le nouveau state créé
        this.setState({
            checked: newChecked,
        });
    };

    render() {
        const { classes } = this.props;
        //On récupère la liste des sports dans une variable
        let sports = this.state.sports;

        return (
            <MuiThemeProvider theme={theme}>
                <div class="container">
                    <List className={classes.root}>
                        <div class="row">
                            {
                                sports.map((sport) => (
                                    <div class="col-md-3">
                                        <ListItem key={sport.id} dense button onClick={this.handleToggle(sport.id)}>
                                            <Checkbox
                                                checked={this.state.checked.indexOf(sport.id) !== -1}
                                                disableRipple
                                                inline
                                            />
                                            <ListItemText primary={sport.name} />
                                        </ListItem>
                                    </div>
                                ))
                            }
                        </div>
                    </List>
                </div>
            </MuiThemeProvider>
        );
    }
}

Sports.propTypes = {
    classes: PropTypes.object.isRequired,
};


// mapping du state global dans les props du composant Sports
const mapStateToProps = (state) => {
	return {
		sports: state.addPlayground.sports
	}
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(Sports));