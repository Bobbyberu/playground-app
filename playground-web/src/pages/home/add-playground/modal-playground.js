import React from 'react';
import Button from '@material-ui/core/Button';
import { Dialog } from '@material-ui/core';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import Stepper from './Stepper';
import { connect } from 'react-redux';

class ModalPlayground extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: '',
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        //console.log(this.props.open)
    }

    handleClose = () => {
        
    };  

    handleChange(event) {
        this.setState({ value: event.target.value });
    }

    handleSubmit(event) {
        alert('You added the playground ' + this.state.value);
        event.preventDefault();
    }

    render() {
       const { open } = this.props.open;
       console.log("open : " + this.props.open)
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

// mapping du state global dans les props du composant Home
const mapStateToProps = (state) => {
    return {
        open: state.open
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(ModalPlayground)
