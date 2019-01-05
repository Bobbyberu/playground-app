import React from 'react'
import PropTypes from 'prop-types';

import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Button from '@material-ui/core/Button'
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Account from '@material-ui/icons/AccountCircle';

import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import Divider from "@material-ui/core/Divider/Divider";
import Drawer from "@material-ui/core/Drawer/Drawer";
import MailIcon from '@material-ui/icons/Mail';
import AddIcon from '@material-ui/icons/AddBox';
import FriendsIcon from '@material-ui/icons/ContactPhone';
import ProfileIcon from '@material-ui/icons/People';
import HistoryIcon from '@material-ui/icons/People';
import UserIcon from '@material-ui/icons/AccountBox';
import LocationIcon from '@material-ui/icons/LocationCity';
import OpinionIcon from '@material-ui/icons/Feedback';

// Styles
const styles = {
    title: {
        flexGrow: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
    root: {
        positionAbsolute: true,
    },
    list: {
        width: 250,

    },
};

// Composant pour le menu et la barre de navigation latérale dynamique
class NavBar extends React.Component {
    state = {
        left: false,
    };

    //fonction pour ouvrir ou fermer le menu
    toggleDrawer = (side, open) => () => {
        this.setState({
            [side]: open,
        });
    };

    render() {
        const { classes } = this.props;

        // Contenu du menu latéral composé de 2 listes
        const sideList = (
            <div className={classes.list}>
                {/* Premiere liste d'icones (taches utilisateur) */}
                <List>
                    {['New Playground', 'Profile'].map((text, index) => (
                        <ListItem button key={text}>
                            <ListItemIcon>{index % 2 === 0 ? <AddIcon /> : <ProfileIcon />}</ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItem>
                    ))}
                </List>
                {/*On divise le menu en 2 parties et on propose la seconde liste d'icones(Taches administrateur)*/}
                <Divider />
                <List>
                    {['Playgrounds', 'Users'].map((text, index) => (
                        <ListItem button key={text}>
                            <ListItemIcon>{index % 2 === 0 ? <LocationIcon /> : <UserIcon />}</ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItem>
                    ))}
                </List>
            </div>
        );

        return(
            <div className={classes.root}>
                {/* Création de la barre de menu */}
                <AppBar position="static">
                    <Toolbar>
                        {/* Icone du menu avec ouverture du menu coulissant au clic*/}
                        <IconButton className={classes.menuButton}
                                    color="inherit"
                                    aria-label="Menu"
                                    onClick={this.toggleDrawer('left', true)}
                        >
                            <MenuIcon />
                        </IconButton>
                        {/* Gestion de la fermeture du menu(drawer) avec appel de la sideList (affichée si open = true, invisible si open = false */}
                        <Drawer open={this.state.left} onClose={this.toggleDrawer('left', false)}>
                            <div
                                tabIndex={0}
                                role="button"
                                onClick={this.toggleDrawer('left', false)}
                                onKeyDown={this.toggleDrawer('left', false)}
                            >
                                {sideList}
                            </div>
                        </Drawer>
                        <Typography variant="title" color="inherit" className={classes.title}>
                            Playgrounds
                        </Typography>
                        <Button className={classes.button} color="inherit">
                            Login
                            <Account/>
                        </Button>
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)


