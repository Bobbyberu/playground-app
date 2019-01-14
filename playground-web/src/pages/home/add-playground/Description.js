import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

const styles = theme => ({
  textField: {
    width: '90%',
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  },
});


class Localisation extends React.Component {
  render() {
    const { classes } = this.props;

    return (
      <TextField
        id="desc"
        label="Description"
        multiline
        rows="4"
        className={classes.textField}
        margin="normal"
        variant="outlined"
        autoFocus
      />
    );
  }
}

Localisation.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Localisation);
