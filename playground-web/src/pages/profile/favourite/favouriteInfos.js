import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';


const styles = theme => ({

});


class favouriteInfos extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { classes } = this.props;

        return (
            'Ces informations seront disponibles après le développement du sprint 3'
        );
    }
}

favouriteInfos.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(favouriteInfos);
