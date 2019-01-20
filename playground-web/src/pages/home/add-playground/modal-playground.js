import React from 'react';
import Button from '@material-ui/core/Button';
import { Dialog } from '@material-ui/core';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Stepper from './Stepper';
import { connect } from 'react-redux';
import AuthService from '../../../services/auth';
import PlaygroundAPI from '../../../services/playground-api';

class ModalPlayground extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: '',
        }
        this.api = new PlaygroundAPI();
        this.auth = new AuthService();
    }

    // gérer le changement de valeur des champs
    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    handleClose = () => {
        // Action à envoyer au store
        const action = { type: "TOGGLE_STATE_MODAL", value: false }
        this.props.dispatch(action)
    };

    handleAdding = () => {
        // Récupération des propriétés du playground en cours d'ajout
        let playground = {
            name: this.props.properties.name,
            isPrivate: this.props.properties.isPrivate,
            covered: this.props.properties.covered,
            latitude: this.props.properties.latitude,
            longitude: this.props.properties.longitude,
            surface: this.props.properties.surface,
            description: this.props.properties.description,
            averageMark: this.props.properties.averageMark,
            image: this.props.properties.image,
            players: this.props.properties.players,
            sports: this.props.properties.sports,
            city: this.props.properties.city,
            address: this.props.properties.street + " " + this.props.properties.cp + " " + this.props.properties.city
        };

        console.log("terrain : " + playground.name + " " + playground.address + " " + playground.sports)

        this.api.postPlayground(playground);
        console.log("xxx")
    }

    render() {
        //destructuration
        const { open } = this.props
        return (
            <div>
                <Dialog open={open} onClose={this.handleClose} maxWidth={"md"} fullWidth >
                    <DialogTitle >Ajouter un nouveau terrain</DialogTitle>
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
                        {/* Condition un peu sale pour désactiver le bouton tant qu'on est pas à la dernière étape
                            Il faudrait définir les étapes du stepper (pour accéder à steps.length) dans un reducer si on a le time */}
                        <Button onClick={this.handleAdding} color="primary" disabled={this.props.step !== 5}>
                            Ajouter le terrain
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
        open: state.toggleModal.open,
        step: state.addPlayground.step,
        properties: state.addPlayground
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(ModalPlayground)
