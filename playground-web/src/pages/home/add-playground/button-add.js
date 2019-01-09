import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';

const styles = theme => ({
    margin: {
        margin: theme.spacing.unit,
        position: 'absolute',
        right: 25,
        bottom: 25,
        zIndex: 1000,
    },
});

const ButtonAdd = ({ classes, onClick}) => (
    <div onClick={onClick}> 
        <Fab size="medium" color="secondary" aria-label="Add" className={classes.margin}>
            <AddIcon />
        </Fab>
    </div>
)

ButtonAdd.propTypes = {
    classes: PropTypes.object.isRequired,
    onClick: PropTypes.func.isRequired,
};

export default withStyles(styles)(ButtonAdd);