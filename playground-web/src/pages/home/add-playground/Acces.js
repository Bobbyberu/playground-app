import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';

const styles = theme => ({
	textField: {
		marginLeft: theme.spacing.unit,
		marginRight: theme.spacing.unit,
	},
});

class Acces extends React.Component {
	state = {
		checkedPublic: false,
		checkedPrivate: false,
	};

	handleChange = name => event => {
		this.setState({ [name]: event.target.checked });
	};

	render() {
		const { classes } = this.props;
		const days = ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche']
		return (
				<div className="container">
					<FormGroup>
						<FormControlLabel
							control={
								<Checkbox
									checked={this.state.checkedPrivate}
									onChange={this.handleChange('checkedPrivate')}
									value="checkedPrivate"
								/>
							}
							label="Privé"
						/>
					</FormGroup>
					<List>
						<div className="row">
							{
								days.map((day) => (
									<div className="col-6">
										<ListItem key={day} dense>
											<div className="col-4">
												<ListItemText primary={day} dense />
											</div>
											<div className="col-4">
												<TextField
													id="open"
													label="Ouverture"
													className={classes.textField}
													type="time"
													name="open"
													margin="none"
													InputLabelProps={{
														shrink: true,
													}}
													dense
												/>
											</div>
											<div className="col-4">
												<TextField
													id="close"
													label="Fermeture"
													className={classes.textField}
													type="time"
													name="close"
													margin="none"
													InputLabelProps={{
														shrink: true,
													}}
													dense
												/>
											</div>
										</ListItem>
									</div>
								))
							}
						</div>
					</List>
				</div>
		);
	}
}

Acces.propTypes = {
	classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Acces);