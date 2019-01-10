import React from 'react';
import Button from '@material-ui/core/Button';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { Dialog } from '@material-ui/core';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Stepper from './Stepper';

const styles = {

};

class ModalPlayground extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            open: this.props.show,
            value: '',
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    // Mise à jour du state lorsque l'on reçoit des props du composant parent
    // Méthode dépréciée, à retravailler
    UNSAFE_componentWillReceiveProps(props) {
        this.setState({ open: props.show })
    }

    handleClose = () => {
        this.setState({ open: false });
    };  

    handleChange(event) {
        this.setState({ value: event.target.value });
    }

    handleSubmit(event) {
        alert('You added the playground ' + this.state.value);
        event.preventDefault();
    }

    render() {
        const { classes } = this.props;

        const { open } = this.state;
        return (
            <div>
                <Dialog open={open} onClose={this.handleClose} maxWidth={"md"} fullWidth >
                    <DialogTitle id="form-dialog-title">Ajouter un nouveau terrain</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Veillez à compléter chaque étape du formulaire avant de passer à la suivante.
                        </DialogContentText>
                    </DialogContent>
                    <Stepper />
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Annuler
                        </Button>
                        <Button onClick={this.handleClose} color="primary">
                            Ajouter
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

ModalPlayground.propTypes = {
    onClose: PropTypes.func.isRequired,
    show: PropTypes.bool
};

export default withStyles(styles)(ModalPlayground)