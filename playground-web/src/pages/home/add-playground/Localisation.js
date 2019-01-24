import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { connect } from 'react-redux';
import NominatimAPI from '../../../services/nominatimAPI';
import Button from '@material-ui/core/Button';
import SearchIcon from '@material-ui/icons/Search';
import Progress from './Progress';
import Snackbar from '@material-ui/core/Snackbar';
import SnackbarContentWrapper from '../../../common-components/snackbar/SnackbarContentWrapper';

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
    state = {
      street: this.props.street,
      cp: this.props.cp,
      city: this.props.city,
      lat: this.props.lat,
      long: this.props.long,
      loading: false,
      success: false,
      error: false,
    }

  // gérer le changement de valeur des champs
  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
      success: false,
    });
  };

  findAddress = () => {
    // On indique que la conversion est en cours
    this.setState({
      loading: true,
    });
    // On convertit l'adresse saisie en coordonnées gps
    NominatimAPI.getPosition(this.state.street + ' ' + this.state.cp + ' ' + this.state.city)
      .then(response => {
        if (response.length !== 0) {
          this.setState({
            lat: response[0].lat,
            long: response[0].lon,
            loading: false,
            success: true
          })
          // On active le champ suivant car les valeurs de l'adresse sont renseignées
          const action = { type: 'HANDLE_REQUIREDFIELDS', value: false }
          this.props.dispatch(action)
        } else {
          this.setState({
            loading: false,
            error: true,
          })
        }
      });
  }

  getSnackbarErrorMessage = () => {
    return 'L\'adresse n\'a pas été reconnue';
  }

  handleSnackbarClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    this.setState({
      error: false
    })
  }

  // Dès que le composant n'est plus rendu sur le DOM on envoie l'action avec les infos des champs renseignés
  componentWillUnmount() {
    const action = { type: 'ADD_ADDRESS', value: [this.state.street, this.state.cp, this.state.city, this.state.lat, this.state.long] };
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
          <Button color="primary" onClick={this.findAddress} disabled={this.state.success}>
            <Progress loading={this.state.loading} success={this.state.success} />
          </Button>
        </form>

        <Snackbar
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
          }}
          open={this.state.error}
          autoHideDuration={6000}
          onClose={this.handleSnackbarClose}
        >
          <SnackbarContentWrapper
            onClose={this.handleSnackbarClose}
            variant="error"
            message={this.getSnackbarErrorMessage()}
          />
        </Snackbar>
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
    city: state.addPlayground.city,
    lat: state.addPlayground.latitude,
    long: state.addPlayground.longitude,
    requiredFields: state.addPlayground.requiredFields
  }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(Localisation));