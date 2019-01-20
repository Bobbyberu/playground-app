import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { connect } from 'react-redux';

const styles = theme => ({
  textField: {
    width: '90%',
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  },
});


class Description extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      description: this.props.description,
    }
  }

  // gérer le changement de valeur des champs
  handleChange = (event) => {
    this.setState({
      description: event.target.value
    });
  };

	// Dès que le composant n'est plus rendu sur le DOM on envoie l'action avec les infos récoltées
  componentWillUnmount(){
    const action = { type: 'SET_DESCRIPTION', value: this.state.description };
    this.props.dispatch(action);
  }

  
  render() {
    const { classes } = this.props;

    return (
      <TextField
        id="desc"
        label="Description"
        multiline
        rows="4"
        className={classes.textField}
        margin="normal"
        variant="outlined"
        value={this.state.description}
        onChange={this.handleChange}
        autoFocus
      />
    );
  }
}

Description.propTypes = {
  classes: PropTypes.object.isRequired,
};

// mapping du state global dans les props du composant Sports
const mapStateToProps = (state) => {
	return {
		description: state.addPlayground.description
	}
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(Description));