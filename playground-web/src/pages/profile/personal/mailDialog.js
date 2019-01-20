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
        width: '100%',
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
    },
});

class MailDialog extends React.Component {
    constructor(props) {
        super(props)
    }

    handleClose = () => {
        // On ferme la fenêtre de dialogue
        const action = { type: "TOGGLE_MAIL_DIALOG", value: false };
        this.props.dispatch(action);
    };

    render() {
        const { open } = this.props
        const { classes } = this.props;

        return (
            <div>
                <Dialog open={open} onClose={this.handleClose} maxWidth={"sm"} fullWidth >
                    <DialogTitle >Modification de l'adresse mail</DialogTitle>
                    <DialogContent className={classes.container}>
                        <DialogContentText >
                            <TextField
                                id="mail"
                                label="Nouvelle adresse mail"
                                type="text"
                                name="mail"
                                margin="normal"
                                variant="outlined"
                                className={classes.textField}
                                required
                                autoFocus
                            />
                            <TextField
                                id="confirmMail"
                                label="Confirmer la nouvelle adresse mail"
                                type="text"
                                name="confirmMail"
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
        open: state.toggleModal.openMailDialog
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(MailDialog));
