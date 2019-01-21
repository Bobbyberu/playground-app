import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import { connect } from 'react-redux';
import PasswordDialog from './passwordDialog'
import MailDialog from './mailDialog'

const styles = theme => ({
    item: {
        marginBottom: theme.spacing.unit
    },
});


class PersonalInfos extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            password: '******',
        };
    }

    displayMailDialog = () => {
        // Action à envoyer au store
        const action = { type: "TOGGLE_MAIL_DIALOG", value: true }
        this.props.dispatch(action)
    }

    displayPasswordDialog = () => {
        // Action à envoyer au store
        const action = { type: "TOGGLE_PASSWORD_DIALOG", value: true }
        this.props.dispatch(action)
    }

    render() {
        const { classes } = this.props;

        return (
            <React.Fragment>
                <PasswordDialog />
                <MailDialog />
                <div>
                    <List>
                        <ListItem className={classes.item}>
                            <ListItemText
                                primary={'Adresse mail'}
                                secondary={this.props.mail}
                            />
                            <ListItemSecondaryAction>
                                <IconButton aria-label="Modifier" onClick={this.displayMailDialog}>
                                    <EditIcon />
                                </IconButton>
                            </ListItemSecondaryAction>
                        </ListItem>
                        <ListItem className={classes.item}>
                            <ListItemText
                                primary={'Mot de passe'}
                                secondary={this.state.password}
                            />
                            <ListItemSecondaryAction >
                                <IconButton aria-label="Modifier" onClick={this.displayPasswordDialog}>
                                    <EditIcon />
                                </IconButton>
                            </ListItemSecondaryAction>
                        </ListItem>
                    </List>
                </div>
            </React.Fragment>
        );
    }
}

PersonalInfos.propTypes = {
    classes: PropTypes.object.isRequired,
};

// mapping du state global dans les props du composant Home
const mapStateToProps = (state) => {
    return {
        openPwd: state.toggleModal.openPasswordDialog,
        openMail: state.toggleModal.openMailDialog,
    }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(PersonalInfos));