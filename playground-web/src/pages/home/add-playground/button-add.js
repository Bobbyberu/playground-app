import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import { connect } from 'react-redux'

const styles = theme => ({
    margin: {
        margin: theme.spacing.unit,
        position: 'absolute',
        right: 25,
        bottom: 25,
        zIndex: 1000,
    },
});

class ButtonAdd extends React.Component {

    displayModal = () => {
        // Action à envoyer au store
        const action = { type: "TOGGLE_STATE_MODAL", value: true }
        this.props.dispatch(action)
    }

    render() {
        const { classes } = this.props

        return (
            <Fab size="medium"
                color="secondary"
                aria-label="Add"
                className={classes.margin}
                onClick={this.displayModal}
            >
                <AddIcon /> 
            </Fab>
        )
    }
}

// mapping du state global dans les props du composant Home
const mapStateToProps = (state) => {
    return {
        open: state.open
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(ButtonAdd));