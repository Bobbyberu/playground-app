import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import NavBar from "../../common-components/nav-bar/nav-bar";
import classNames from 'classnames';
import green from '@material-ui/core/colors/green';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import PersonalInfos from './personal/personalInfos';
import FavouriteInfos from './favourite/favouriteInfos';
import Avatar from '../../pictures/bob.PNG';
import SumUp from './sumUp'
import PlaygroundAPI from '../../services/playground-api';
import AuthService from '../../services/auth';

const theme = createMuiTheme({
    palette: {
        primary: {
            main: green[500],
            contrastText: '#fff'
        },
        secondary: {
            main: green[500],
            contrastText: '#fff'
        }
    },
    overrides: {
        MuiExpansionPanel: {
            root: {
                marginBottom: 10,
            },
        },
        MuiListItemText: {
            root: {
                marginRight: 100,
            },
        },
    },
});

const styles = theme => ({
    leftColumn: {
        width: '20%',
        display: 'inline-block',
        verticalAlign: 'top',
        marginTop: theme.spacing.unit * 4,
        marginLeft: '5%'
    },
    rightColumn: {
        width: '70%',
        display: 'inline-block',
        marginRight: '5%'
    },
    container: {
        marginTop: theme.spacing.unit * 6,
        paddingLeft: theme.spacing.unit * 2,
        paddingRight: theme.spacing.unit * 2,
        borderLeft: `2px solid ${theme.palette.divider}`,
    },
    divider: {
        padding: theme.spacing.unit * 2,
    },
    img: {
        width: '60%',
        borderRadius: 1000,
    }
});

class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: 0,
            birthday: '',
            mail: '',
            password: '',
            username: '',
        };
        this.api = new PlaygroundAPI();
        this.authService = new AuthService();
    }

    async componentDidMount() {
        //Récuperer le mail du user connecté
        let getToken = await this.authService.getToken();
        let token = await this.authService.decodeToken();
        this.setState({
            mail: token.sub
        });
        
        //Récuperer les infos complémentaires à partir de l'email
        await this.api.getUserByMail(this.state.mail)
            .then(response => {
                this.setState({
                    userId: response.id,
                    username: response.username,
                    birthday: response.birthDate,
                    password: response.password,
                });
            })  
            .catch(err => console.log(err));

        console.log(token)
    }

    render() {
        const { classes } = this.props;

        return (
            <MuiThemeProvider theme={theme}>
                <NavBar searchbar={false} />
                <div className={classNames(classes.leftColumn, classes.divider)}>
                    <SumUp username={this.state.username} birthDate={this.state.birthday} />
                </div>
                <div className={classNames(classes.rightColumn, classes.container)}>
                    <ExpansionPanel>
                        <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                            <Typography variant="h6">Informations de connexion</Typography>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails>
                            <PersonalInfos userId={this.state.userId} mail={this.state.mail} password={this.state.password} />
                        </ExpansionPanelDetails>
                    </ExpansionPanel>
                    <ExpansionPanel>
                        <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                            <Typography variant="h6">Préférences</Typography>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails>
                            <FavouriteInfos />
                        </ExpansionPanelDetails>
                    </ExpansionPanel>
                </div>
            </MuiThemeProvider>
        );
    }
}

Profile.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Profile);