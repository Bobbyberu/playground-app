import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Localisation from './Localisation';
import Sports from './Sports';
import Upload from './Upload';
import Description from './Description';
import Acces from './Acces';

const styles = theme => ({
	root: {
		width: '100%',
	},
	Button: {
		marginTop: theme.spacing.unit * 4,
		marginLeft: theme.spacing.unit * 8,
	},
	instructions: {
		marginTop: theme.spacing.unit * 2,
		marginLeft: theme.spacing.unit * 8,
		marginBotton: theme.spacing.unit,
	}
});

function getSteps() {
	return ['Localisation', 'Image', 'Acces', 'Sports', 'Description'];
}

function getStepContent(stepIndex) {
	switch (stepIndex) {
		case 0:
			return <Localisation />
		case 1:
			return <Upload />
		case 2:
			return <Acces />
		case 3:
			return <Sports />
		case 4:
			return <Description />
		default:
			return 'Unknown stepIndex';
	}
}

// Composant Stepper material-ui pour afficher les informations à remplir lors de l'ajout d'un playground
class StepperModal extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			activeStep: 0,
		}
	};

	handleNext = () => {
		this.setState(state => ({
			activeStep: state.activeStep + 1,
		}));
	};

	handleBack = () => {
		this.setState(state => ({
			activeStep: state.activeStep - 1,
		}));
	};

	handleReset = () => {
		this.setState({
			activeStep: 0,
		});
	};

	render() {
		const { classes } = this.props;
		const steps = getSteps();
		const { activeStep } = this.state;

		return (
			<div className={classes.root}>
				{/* Retourne le contenu de la bonne étape */}
				<Stepper activeStep={activeStep} alternativeLabel>
					{steps.map(label => {
						return (
							<Step key={label}>
								<StepLabel>{label}</StepLabel>
							</Step>
						);
					})}
				</Stepper>
				<div>
					{/* Affichage du boutton 'Reset' une fois que toutes les étapes sont complétées */}
					{this.state.activeStep === steps.length ? (
						<div>
							<Typography className={classes.instructions}>All steps completed</Typography>
							<Button onClick={this.handleReset}>Reset</Button>
						</div>
					) : (
							<div>
								<Typography className={classes.instructions}>{getStepContent(activeStep)}</Typography>
								<div>
									<Button
										disabled={activeStep === 0}
										onClick={this.handleBack}
										className={classes.Button}
									>
										Back
                   					 </Button>
									{/* Affichage du boutton 'finish' si on est sur la dernière étape */}
									<Button variant="contained" color="primary" onClick={this.handleNext} className={classes.Button}>
										{activeStep === steps.length - 1 ? 'Finish' : 'Next'}
									</Button>
								</div>
							</div>
						)}
				</div>
			</div>
		);
	}
}

StepperModal.propTypes = {
	classes: PropTypes.object,
};

export default withStyles(styles)(StepperModal);