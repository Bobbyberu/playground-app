import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

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
  render() {
    const { classes } = this.props;

    return (
      <form className={classes.container} noValidate autoComplete="off">
        <TextField
          id="address-input"
          label="Adresse"
          className={classes.textFieldLong}
          type="text"
          name="address"
          margin="normal"
          variant="outlined"
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
          required
        />
        <TextField
          id="ville-input"
          label="Ville"
          className={classes.textField}
          type="text"
          name="ville"
          margin="normal"
          variant="outlined"
          required
        />
      </form>
    );
  }
}

Localisation.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Localisation);
