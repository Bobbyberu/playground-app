import React from 'react';
import Button from '@material-ui/core/Button';
import { Dialog } from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import { connect } from 'react-redux';

const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    width : '100%',
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  },
});

class PasswordDialog extends React.Component {
  constructor(props) {
    super(props)
  }

  handleClose = () => {
    // On ferme la fenêtre de dialogue
    const action = { type: "TOGGLE_PASSWORD_DIALOG", value: false };
    this.props.dispatch(action);
  };

  render() {
    const { open } = this.props
    const { classes } = this.props;

    return (
      <div>
        <Dialog open={open} onClose={this.handleClose} maxWidth={"sm"} fullWidth >
          <DialogTitle >Modification du mot de passe</DialogTitle>
          <DialogContent className={classes.container}>
            <DialogContentText >
              <TextField
                id="password"
                label="Nouveau mot de passe"
                type="password"
                name="password"
                margin="normal"
                variant="outlined"
                className={classes.textField}
                required
                autoFocus
              />
              <TextField
                id="confirmPassword"
                label="Confirmer le nouveau mot de passe"
                type="password"
                name="confirmPassword"
                margin="normal"
                variant="outlined"
                className={classes.textField}
                required
              />
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="primary">
              Annuler
                        </Button>
            <Button onClick={this.handleClose} color="primary">
              Valider
                        </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }
}

// mapping du state global dans les props du composant Home
const mapStateToProps = (state) => {
  return {
    open: state.toggleModal.openPasswordDialog
  }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(PasswordDialog));
