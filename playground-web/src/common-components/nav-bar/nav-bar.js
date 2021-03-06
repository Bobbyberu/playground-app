import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

import { withStyles } from "@material-ui/core/styles";
import { fade } from "@material-ui/core/styles/colorManipulator";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";

import Divider from "@material-ui/core/Divider/Divider";
import Drawer from "@material-ui/core/Drawer/Drawer";
import List from "@material-ui/core/List/List";
import ListItem from "@material-ui/core/ListItem/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import Snackbar from "@material-ui/core/Snackbar";

import Searchbar from "../../pages/home/components/searchbar/searchbar";
import AuthService from "../../services/auth";
import SnackbarContentWrapper from "../../common-components/snackbar/SnackbarContentWrapper";
import logo from "../../assets/img/logo_full.png";

import HomeIcon from "@material-ui/icons/Home";
import ProfileIcon from "@material-ui/icons/People";
import CommentIcon from "@material-ui/icons/Comment";
import FlagIcon from "@material-ui/icons/Flag";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import Account from "@material-ui/icons/AccountCircle";
import ExitToApp from "@material-ui/icons/ExitToApp";

// Styles
const styles = theme => ({
  title: {
    marginTop: 5
  },
  navbarRight: {
    display: "flex",
    flexGrow: 1,
    justifyContent: "space-between"
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
    color: theme.palette.common.white
  },
  button: {
    color: theme.palette.common.white
  },
  root: {
    left: 0,
    right: 0,
    zIndex: 1000,
    height: 64
  },
  logoPlayground: {
    height: 45,
    position: "absolute",
    top: 12
  },
  list: {
    width: 250
  },
  search: {
    position: "relative",
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    "&:hover": {
      backgroundColor: fade(theme.palette.common.white, 0.25)
    },
    marginLeft: 0,
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      marginLeft: theme.spacing.unit,
      width: "auto"
    }
  },
  searchIcon: {
    width: theme.spacing.unit * 9,
    height: "100%",
    position: "absolute",
    pointerEvents: "none",
    display: "flex",
    alignItems: "center",
    justifyContent: "center"
  },
  inputRoot: {
    color: "inherit",
    width: "100%"
  },
  inputInput: {
    paddingTop: theme.spacing.unit,
    paddingRight: theme.spacing.unit,
    paddingBottom: theme.spacing.unit,
    paddingLeft: theme.spacing.unit * 10,
    transition: theme.transitions.create("width"),
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      width: 120,
      "&:focus": {
        width: 200
      }
    }
  },
  overlay: {
    zIndex: 1000,
    marginRight: 40
  },
  link: {
    color: fade(theme.palette.common.white, 1),
    "&:hover": {
      textDecoration: "none",
      color: fade(theme.palette.common.white, 0.8)
    }
  },
  closeSnackbar: {
    padding: theme.spacing.unit / 2
  }
});

// Composant pour le menu et la barre de navigation latérale dynamique
class NavBar extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      left: false,
      loggedIn: false,
      snackbarOpen: false
    };

    this.logout = this.logout.bind(this);
  }

  componentDidMount() {
    this.setState({
      loggedIn: AuthService.loggedIn()
    });
  }

  //fonction pour ouvrir ou fermer le menu
  toggleDrawer = (side, open) => () => {
    this.setState({
      [side]: open
    });
  };

  // log out the user and toggle the snackbar
  logout() {
    AuthService.logout();
    this.setState({
      loggedIn: false,
      snackbarOpen: true
    });
  }

  handleSnackbarClose(event, reason) {
    if (reason === "clickaway") {
      return;
    }

    this.setState({
      snackbarOpen: false
    });
  }

  renderLoginPart() {
    const { classes } = this.props;

    if (this.state.loggedIn) {
      return (
        <Button className={classes.button} onClick={this.logout}>
          <ExitToApp />
        </Button>
      );
    } else {
      return (
        <Link to="/login" className={classes.link}>
          <Button className={classes.button}>
            Login
            <Account />
          </Button>
        </Link>
      );
    }
  }

  render() {
    const { classes } = this.props;

    // Contenu du menu latéral composé de 2 listes
    const sideList = (
      <div className={classes.list}>
        {/* Premiere liste d'icones (taches utilisateur) */}
        <List>
          <Link to="/" className={classes.link}>
            <ListItem button>
              <ListItemIcon>
                <HomeIcon />
              </ListItemIcon>
              <ListItemText primary={"Accueil"} />
            </ListItem>
          </Link>
          <Link to="/profile" className={classes.link}>
            <ListItem button>
              <ListItemIcon>
                <ProfileIcon />
              </ListItemIcon>
              <ListItemText primary={"Profil"} />
            </ListItem>
          </Link>
        </List>
      </div>
    );

    let adminPages;
    if (AuthService.isAdmin()) {
      adminPages = (
        <React.Fragment>
          <Divider />
          <List>
            <Link to="/report/playgrounds">
              <ListItem button>
                <ListItemIcon>
                  <FlagIcon />
                </ListItemIcon>
                <ListItemText primary={"Signalement playgrounds"} />
              </ListItem>
            </Link>
            <Link to="/report/comments">
              <ListItem button>
                <ListItemIcon>
                  <CommentIcon />
                </ListItemIcon>
                <ListItemText primary={"Signalement avis"} />
              </ListItem>
            </Link>
          </List>
        </React.Fragment>
      );
    }

    return (
      <div className={classes.root}>
        {/* Création de la barre de menu */}
        <AppBar position="fixed">
          <Toolbar>
            {/* Icone du menu avec ouverture du menu coulissant au clic*/}
            <IconButton
              className={classes.menuButton}
              aria-label="Menu"
              onClick={this.toggleDrawer("left", true)}
            >
              <MenuIcon />
            </IconButton>
            {/* Gestion de la fermeture du menu(drawer) avec appel de la sideList (affichée si open = true, invisible si open = false */}
            <Drawer
              open={this.state.left}
              onClose={this.toggleDrawer("left", false)}
            >
              <div
                tabIndex={0}
                role="button"
                onClick={this.toggleDrawer("left", false)}
                onKeyDown={this.toggleDrawer("left", false)}
              >
                {sideList}
                {adminPages}
              </div>
            </Drawer>
            <div className={classes.navbarRight}>
              <Link to="/" className={classes.link}>
                <img alt="" src={logo} className={classes.logoPlayground} />
              </Link>
              {this.props.searchbar && (
                <div className={classes.overlay}>
                  <Searchbar />
                </div>
              )}
            </div>
            <div>{this.renderLoginPart()}</div>
          </Toolbar>
        </AppBar>

        <Snackbar
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "left"
          }}
          open={this.state.snackbarOpen}
          autoHideDuration={6000}
          onClose={this.handleSnackbarClose.bind(this)}
        >
          <SnackbarContentWrapper
            onClose={this.handleSnackbarClose.bind(this)}
            variant="info"
            message={"Vous êtes déconnecté"}
          />
        </Snackbar>
      </div>
    );
  }
}

NavBar.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(NavBar);
