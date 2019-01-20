import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { connect } from 'react-redux';

const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    width: '20%',
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  },
  textFieldLong: {
    width: '35%',
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  },
});


class Localisation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      street: this.props.street,
      cp: this.props.cp,
      city: this.props.city,
    }
  }

  // gérer le changement de valeur des champs
  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  // Dès que le composant n'est plus rendu sur le DOM on envoie l'action avec les infos des champs renseignés
  componentWillUnmount(){
    const action = { type: 'ADD_ADDRESS', value: [this.state.street , this.state.cp , this.state.city] };
    this.props.dispatch(action);
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <form className={classes.container} noValidate autoComplete="off">
          {/* 3 champs pour l'adresse, le code postal et la ville */}
          <TextField
            id="street-input"
            label="Adresse"
            className={classes.textFieldLong}
            type="text"
            name="street"
            margin="normal"
            variant="outlined"
            value={this.state.street}
            onChange={this.handleChange}
            required
            autoFocus
          />
          <TextField
            id="cp-input"
            label="Code Postal"
            className={classes.textField}
            type="text"
            name="cp"
            margin="normal"
            variant="outlined"
            value={this.state.cp}
            onChange={this.handleChange}
            required
          />
          <TextField
            id="ville-input"
            label="Ville"
            className={classes.textField}
            type="text"
            name="city"
            margin="normal"
            variant="outlined"
            value={this.state.city}
            onChange={this.handleChange}
            required
          />
        </form>
      </div>
    );
  }
}

Localisation.propTypes = {
  classes: PropTypes.object.isRequired,
};

// mapping du state global dans les props du composant Localisation
const mapStateToProps = (state) => {
  return {
    street: state.addPlayground.street,
    cp: state.addPlayground.cp,
    city: state.addPlayground.city
  }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(Localisation));