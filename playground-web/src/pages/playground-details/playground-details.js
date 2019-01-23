import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import { fade } from '@material-ui/core/styles/colorManipulator';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Favorite from '@material-ui/icons/Favorite';
import FavoriteBorder from '@material-ui/icons/FavoriteBorder';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Divider from '@material-ui/core/Divider';
import Emoji from '../../common-components/emoji/emoji';
import green from '@material-ui/core/colors/green';
import grey from '@material-ui/core/colors/grey';
import defaultPlayground from '../../assets/img/default_playground.png';
import PlaygroundAPI from '../../services/playground-api';
import AuthService from '../../services/auth';

// Override de certains éléments de la card
const theme = createMuiTheme({
  palette: {
    primary: {
      main: green[700],
    },
    secondary: {
      main: grey[50],
    },
  },
  overrides: {
    MuiListItem: {
      gutters: {
        paddingLeft: 0,
        paddingRight: 0,
      },
      root: {
        paddingTop: 0,
        paddingBottom: 0,
      },
    },
    MuiAvatar: {
      colorDefault: {
        backgroundColor: 'transparent',
      },
    },
    MuiCardContent: {
      root: {
        paddingBottom: 0,
      },
    },
    MuiTypography: {
      root: {
        paddingBottom: 0,
      },
    },
  },
});

// Styles appliqués aux éléments de la card
const styles = theme => ({
  card: {
    width: 360,
  },
  media: {
    height: 140,
  },
  button: {
    display: 'flex',
    flexGrow: 1,
    justifyContent: 'space-between',
  },
  title: {
    marginBottom: 0,
    fontWeight: 'bold',
  },
  subtitle: {
    fontStyle: 'italic',
    fontSize: 13,
    marginTop: 0,
  },
  private: {
    fontWeight: 'bold',
    fontSize: 12,
  },
  avatar: {
    height: 'auto',
    width: 'auto',
  },
  section1: {
    marginBottom: theme.spacing.unit * 2,
  },
  section2: {
    marginBottom: theme.spacing.unit,
  },
  link: {
    color: green[500],
    '&:hover': {
      textDecoration: 'none',
      color: fade(green[500], 0.8),
    },
  },
});

/*
 Contenu de la popup avec un element material ui Card
 */
class PlaygroundDetails extends React.Component {
  constructor(props) {
    super(props);

    this.state = ({
      favorited: false
    });

    this.toggleFavorite = this.toggleFavorite.bind(this);
  }

  componentDidMount() {
    if (AuthService.loggedIn()) {
      let user = AuthService.getUser();

      PlaygroundAPI.isFavorite(user.id, this.props.playground.id)
        .then(response => {
          this.setState({
            favorited: response
          });
        })
        .catch(err => console.log(err));
    }
  }

  toggleFavorite() {
    this.setState({
      favorited: !this.state.favorited
    });

    let user = AuthService.getUser();

    PlaygroundAPI.updateFavorite(user.id, this.props.playground.id)
      .catch(err => {
        console.log(err);
        this.setState({
          favorited: !this.state.favorited
        });
      });
  }

  displayFavoriteIcon() {
    // On modifie l'icône 'favori' en fonction de l'état du playground sélectionné
    return (
      this.state.favorited ? <Favorite /> : <FavoriteBorder />
    );
  }

  render() {
    const { classes } = this.props;
    let playgroundImg = this.props.playground.image ? this.props.playground.image : defaultPlayground;

    return (
      <MuiThemeProvider theme={theme}>
        <div className="global">
          <Card className={classes.card}>
            {/* Image associée au playground */}
            <CardMedia
              className={classes.media}
              image={playgroundImg}
              title="Photo du playground"
            />
            {/* Informations sur le playground */}
            <CardContent>
              <div className={classes.section1}>
                <Typography className={classes.title} gutterBottom variant="h5">
                  {this.props.playground.name}
                </Typography>
                <Typography className={classes.subtitle} component="h3">
                  {this.props.playground.address}
                </Typography>
              </div>
              <div>
                <Typography className={classes.private} component="h3">
                  {this.props.playground.private ? 'Privé' : 'Public'}
                </Typography>
                <Divider variant="fullWidth" />
              </div>
              <div className={classes.section2}>
                <Typography className={classes.p} component="p">
                  {/* Récupération des sports d'un playground associés à leur emoji */}
                  <List>
                    {
                      this.props.playground.sports.map(sport => (
                        <ListItem>
                          <Emoji symbol={sport.symbol} label={sport.name} />
                          <ListItemText primary={sport.name} />
                        </ListItem>
                      ))
                    }
                  </List>
                </Typography>
              </div>
            </CardContent>
            {/* Actions associés à l acard (favoris et détails) */}
            <CardActions className={classes.button}>
              <Link to={'/details/' + this.props.playground.id} className={classes.link}>
                <Button size="small" color="primary">
                  Détails
                </Button>
              </Link>
              {AuthService.loggedIn() &&
                <IconButton aria-label="Ajouter aux favoris" color="primary" onClick={() => this.toggleFavorite()}>
                  {this.displayFavoriteIcon()}
                </IconButton>
              }
            </CardActions>
          </Card>
        </div>
      </MuiThemeProvider>
    );
  }
}

PlaygroundDetails.propTypes = {
  playground: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired,
};

// La fonction mapStateToProps permet d'abonner le composant aux changements du store Redux
export default withStyles(styles)(PlaygroundDetails);
