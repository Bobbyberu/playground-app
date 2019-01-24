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
import TextField from '@material-ui/core/TextField';
import { connect } from 'react-redux';

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
			return 'Etape inconnue';
	}
}

// Composant Stepper material-ui pour afficher les informations à remplir lors de l'ajout d'un playground
class StepperModal extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			name: this.props.name,
		}
	};

	// Dès que le composant n'est plus rendu sur le DOM on envoie l'action avec les infos récoltées
	componentWillUnmount() {
		const action = { type: 'RESET' }
		this.props.dispatch(action)
	}

	//Gestion des boutons
	handleNext = () => {
		const action = { type: 'SET_STEP', value: this.props.step + 1 }
		this.props.dispatch(action)
	};

	handleBack = () => {
		const action = { type: 'SET_STEP', value: this.props.step - 1 }
		this.props.dispatch(action)
	};

	handleReset = () => {
		const action = { type: 'RESET' }
		this.props.dispatch(action)
	};

	// gérer le changement de valeur du champ name
	handleChange = (event) => {
		this.setState({
		  [event.target.name]: event.target.value
		});
	  };
	

	handleName = () => {
		const action = { type: 'SET_NAME', value: this.state.name }
		this.props.dispatch(action)
	}

	render() {
		const { classes } = this.props
		const steps = getSteps()
		return (
			<div className={classes.root}>
				{/* Retourne le contenu de la bonne étape */}
				<Stepper activeStep={this.props.step} alternativeLabel>
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
					{this.props.step === steps.length ? (
						<div>
							<div>
								{/* Affichage du nom du terrain à l'étape de validation*/}
								<Typography className={classes.instructions}>Toutes les étapes ont été complétées. A présent, choisissez un nom à votre terrain.</Typography>
								<TextField
									id="name"
									label="Name"
									type="text"
									name="name"
									margin="normal"
									variant="outlined"
									value={this.state.name}
									onChange={this.handleChange}
									onBlur={this.handleName}
									required
									className={classes.instructions}
								/>
							</div>
							<Button className={classes.Button} onClick={this.handleReset}>Réinitialiser</Button>
						</div>
					) : (
							<div>
								<Typography className={classes.instructions}>{getStepContent(this.props.step)}</Typography>
								<div>
									<Button
										disabled={this.props.step === 0}
										onClick={this.handleBack}
										className={classes.Button}
									>
										Précédent
                   					 </Button>
									{/* Affichage du boutton 'finish' si on est sur la dernière étape */}
									<Button  color="primary" onClick={this.handleNext} className={classes.Button} disabled={this.props.requiredFields}>
										{this.props.step === steps.length - 1 ? 'Valider' : 'Suivant'}
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

// mapping du state global dans les props du composant StepperModal
const mapStateToProps = state => ({
	step: state.addPlayground.step,
	name: state.addPlayground.name,
	requiredFields: state.addPlayground.requiredFields
});

// La fonction mapStateToProps permet d'abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(StepperModal));
