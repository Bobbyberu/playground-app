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
import PlaygroundAPI from '../../../services/playground-api';
import AuthService from '../../../services/auth';
import { Redirect } from 'react-router-dom';

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
        super(props);
        this.state = {
            newMail: '',
            confirmMail: '',
            redirect: false
        };
    }

    // gérer le changement de valeur des champs
    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    handleClose = () => {
        // On ferme la fenêtre de dialogue
        const action = { type: "TOGGLE_MAIL_DIALOG", value: false };
        this.props.dispatch(action);
    };

    handleUpdating = () => {
        PlaygroundAPI.getUserById(this.props.userId)
            .then(response => {
                if (this.state.newMail === this.state.confirmMail) {
                    response.mail = this.state.newMail;
                }
                PlaygroundAPI.updateUser(response)

                // On change le state de la modale
                const action = { type: "TOGGLE_MAIL_DIALOG", value: false };
                this.props.dispatch(action);

                // Redirection de l'utilisateur pour se reconnecter
                this.setState({
                    redirect: true,
                })
            });
    }

    handleRedirect() {
        AuthService.logout();
        return <Redirect to="/login" />;
    }

    renderDialog(props) {
        const { open } = this.props
        const { classes } = this.props;

        return (
            <div>
                <Dialog open={open} onClose={this.handleClose} maxWidth={"sm"} fullWidth >
                    <DialogTitle >Modification de l'adresse mail</DialogTitle>
                    <DialogContent className={classes.container}>
                        <DialogContentText >
                            <TextField
                                id="newMail"
                                label="Nouvelle adresse mail"
                                type="text"
                                name="newMail"
                                margin="normal"
                                variant="outlined"
                                className={classes.textField}
                                onChange={this.handleChange}
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
                                onChange={this.handleChange}
                                required
                            />
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Annuler
                        </Button>
                        <Button onClick={this.handleUpdating} color="primary">
                            Valider
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

    render() {
        console.log(this.state.redirect)
        return this.state.redirect ? this.handleRedirect() : this.renderDialog(this.props);
    }
}

// mapping du state global dans les props du composant Home
const mapStateToProps = (state) => {
    return {
        open: state.toggleModal.openMailDialog,
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(MailDialog));
