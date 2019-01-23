import React from 'react';
import { connect } from 'react-redux';

import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';

import AuthService from '../../../services/auth';
import PlaygroundAPI from '../../../services/playground-api';

import { withStyles } from '@material-ui/core/styles';

const styles = {
    global: {
        padding: 40,
        paddingBottom: 10
    },
    reason: {
        marginTop: 20,
        marginBottom: 40,
    }
}

class ReportPlaygroundModal extends React.Component {
    constructor(props) {
        super(props);

        this.state = ({
            reason: '',
            description: '',
            completed: false
        });

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value,
        });
    }

    handleSubmit() {
        let user = AuthService.getUser();

        PlaygroundAPI.reportPlayground(user, this.props.playground, this.state.reason)
            .then(() => {
                this.setState({
                    completed: true
                });
            })
            .catch(err => console.log(err));
    }

    handleClose() {
        // Action à envoyer au store
        const action = { type: "TOGGLE_REPORT_PLAYGROUND", value: false }
        this.props.dispatch(action);
    }

    handleEnter() {
        // Triggers whenever modal is opened
        this.setState({
            reason: '',
            description: '',
            completed: false
        });
    }

    render() {
        const { classes, openReportPlayground } = this.props;

        const content = this.state.completed ? this.renderCompleted() : this.renderForm(classes);

        return (
            <Dialog open={openReportPlayground} onEnter={this.handleEnter.bind(this)} onClose={this.handleClose.bind(this)} maxWidth={"md"} fullWidth>
                <div className={classes.global}>
                    {content}
                </div>
            </Dialog>
        );
    }

    renderForm(classes) {
        return (
            <React.Fragment>
                <DialogTitle><span role="img">🏴</span> Signaler un playground</DialogTitle>
                <ValidatorForm ref="form"
                    onSubmit={this.handleSubmit}
                    onError={errors => console.log(errors)}>
                    <DialogContent>
                        <div>
                            <TextValidator fullWidth
                                className={classes.reason}
                                onChange={this.handleInputChange}
                                name="reason"
                                placeholder="Pourquoi signaler ce terrain ?"
                                value={this.state.reason}
                                variant="outlined"
                                validators={['required']}
                                errorMessages={['Champ obligatoire']}
                            />
                        </div>
                        <TextValidator multiline fullWidth
                            onChange={this.handleInputChange}
                            name="description"
                            rows="4"
                            placeholder="Détaillez les raisons de votre signalement si possible"
                            value={this.state.description}
                            variant="outlined"
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">Annuler</Button>
                        <Button type="submit" color="primary">Publier</Button>
                    </DialogActions>
                </ValidatorForm>
            </React.Fragment>
        );
    }

    renderCompleted() {
        return (
            <React.Fragment>
                <DialogContent>Votre signalement sur {this.props.playground.name} a bien été envoyé !</DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary">OK</Button>
                </DialogActions>
            </React.Fragment>
        );
    }
}

// global state mapping in Playground.js props
const mapStateToProps = (state) => {
    return {
        openReportPlayground: state.toggleModal.openReportPlayground
    }
}

export default connect(mapStateToProps)(withStyles(styles)(ReportPlaygroundModal));