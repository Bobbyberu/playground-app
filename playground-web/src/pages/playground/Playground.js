import React, { Component } from 'react';
import { connect } from 'react-redux';

import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Divider from '@material-ui/core/Divider';
import Emoji from '../../common-components/emoji/emoji';
import { withStyles } from '@material-ui/core/styles';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';

import AddCommentModal from './components/comments/AddCommentModal';
import AuthService from '../../services/auth';
import CommentSection from './components/comments/CommentSection';
import Navbar from '../../common-components/nav-bar/nav-bar';
import NotFound from '../404/NotFound';
import PlaygroundAPI from '../../services/playground-api';
import ReportCommentModal from './components/comments/ReportCommentModal';
import ReportPlaygroundModal from './components/ReportPlaygroundModal';
import StarRating from './components/StarRating';
import Timetable from './components/Timetable';

import './playground.css';
import defaultPlayground from '../../assets/img/default_playground.png';
import FavoriteIcon from '@material-ui/icons/Star';
import FavoriteIconEmpty from '@material-ui/icons/StarBorder';
import Flag from '@material-ui/icons/Flag';
import grey from '@material-ui/core/colors/grey';
import green from '@material-ui/core/colors/green';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: green[700],
    },
    secondary: {
      main: grey[50],
    },
  }
});

// Styles appliqués aux éléments de la card
const styles = theme => ({
  global: {
    height: '100%'
  },
  card: {
    width: 1400,
    marginRight: 'auto',
    marginLeft: 'auto',
    height: '100%',
    borderRadius: 0
  },
  cardContent: {
    paddingLeft: 40,
    paddingRight: 40
  },
  topIcon: {
    fontSize: 50
  },
  report: {
    top: 0,
    right: 0,
    position: 'absolute',
    bottom: '50%',
    left: '97%'
  },
  firstDivider: {
    marginBottom: 30
  },
  divider: {
    marginTop: 45,
    marginBottom: 45
  },
  media: {
    objectFit: 'cover',
    height: 250
  },
  favorite: {
    padding: 0,
    marginBottom: 10,
    marginLeft: 10
  },
  button: {
    display: 'flex',
    flexGrow: 1,
    justifyContent: 'space-between',
  },
  titleRow: {
    marginRight: 0
  },
  ratingRow: {
    height: 40
  },
  playgroundName: {
    textAlign: 'left',
    fontSize: '3vw',
    marginBottom: 5
  },
  playgroundAddress: {
    fontStyle: 'italic',
    textAlign: 'left',
    fontSize: '1.5vw',
    color: grey[700]
  },
  availability: {
    textAlign: 'left',
    fontSize: '1vw',
    fontWeight: 'bold',
    marginTop: 10
  },
  sectionTitle: {
    textAlign: 'left',
    marginTop: 10
  },
  timetable: {
    marginLeft: 30,
    marginRight: 30,
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
});

class Playground extends Component {
  constructor(props) {
    super(props);

    this.state = ({
      loading: true,
      playground: 0,
      favorited: false,
      loggedIn: false
    });

    this.api = new PlaygroundAPI();
    this.auth = new AuthService();
  }

  componentDidMount() {
    this.api.getPlaygroundById(this.props.id)
      .then(response => {
        this.setState({
          playground: response,
          loggedIn: this.auth.loggedIn()
        });
      })
      .catch(err => console.log(err));
  }

  toggleFavorite = () => {
    this.setState({
      favorited: !this.state.favorited
    });
  }

  toggleReportPlayground = () => {
    // Action will be sent to store
    const action = { type: "TOGGLE_REPORT_PLAYGROUND", value: true };
    this.props.dispatch(action);
  }

  displayFavoriteIcon = (classes) => {
    if (this.state.favorited) {
      return <FavoriteIcon className={classes.topIcon} />;
    } else {
      return <FavoriteIconEmpty className={classes.topIcon} />;
    }
  }

  render() {
    if (this.state.playground === 0) {
      return (<div>Loading mon petit pote</div>)
    } else if (!this.state.playground) {
      return (
        <React.Fragment>
          <NotFound message={'Ce playground n\'a pas pu être trouvé!'} />
        </React.Fragment>
      );
    } else {

      const { classes, reportedComment } = this.props;

      let playgroundImg = this.state.playground.image ? this.state.playground.image : defaultPlayground;
      return (
        <MuiThemeProvider theme={theme}>
          <Navbar searchbar={false} />
          <div className={classes.global}>
            <Card className={classes.card}>
              {/* Image associée au playground */}
              <CardMedia
                component="img"
                className={classes.media}
                image={playgroundImg}
                title={this.state.playground.description}
              />
              {/* Informations sur le playground */}
              <CardContent className={classes.cardContent}>
                <div>
                  <div className={classes.titleRow + ' row col-12 justify-content'}>
                    <Typography className={classes.playgroundName} variant="h5">
                      {this.state.playground.name}
                    </Typography>

                    {/** available only if authenticated */}
                    {this.state.loggedIn &&
                      <React.Fragment>
                        {/* Favorite a card */}
                        <CardActions className={classes.favorite} title="Ajouter ce playground à vos favoris">
                          <IconButton color="primary" className={classes.favorite} aria-label="Ajouter aux favoris" onClick={this.toggleFavorite}>
                            {this.displayFavoriteIcon(classes)}
                          </IconButton>
                        </CardActions>

                        {/* Report a playground */}
                        <CardActions className={classes.report} title="Signaler un playground">
                          <IconButton color="primary" aria-label="Ajouter aux favoris" onClick={this.toggleReportPlayground}>
                            <Flag className={classes.topIcon} />
                          </IconButton>
                        </CardActions>
                      </React.Fragment>
                    }
                  </div>

                  <Typography className={classes.playgroundAddress} component="h3">
                    {this.state.playground.address}
                  </Typography>
                </div>
                <div>
                  <div className={classes.ratingRow + ' row col-12 justify-content-between'}>
                    <Typography className={classes.availability} component="h3">
                      {this.state.playground.private ? 'Privé' : 'Public'}
                    </Typography>
                    <StarRating rating={this.state.playground.averageMark} number={true} />
                  </div>
                  <Divider className={classes.firstDivider} variant="fullWidth" />
                </div>
                <div className="col-12">
                  {/* Récupération des sports d'un playground associés à leur emoji */}
                  <List className="row">
                    {
                      this.state.playground.sports.map(sport => (
                        <ListItem className="col-4" key={sport.id}>
                          <Emoji symbol={sport.symbol} label={sport.name} />
                          <ListItemText primary={sport.name} />
                        </ListItem>
                      ))
                    }
                  </List>
                </div>
                {this.state.playground.timetable &&
                  <div id="timetable-section">
                    <Divider className={classes.divider} variant="fullWidth" />
                    <div className={classes.sectionTitle}><h5>⏰ Horaires</h5></div>
                    <div className={classes.timetable}>
                      <Timetable timetable={this.state.playground.timetable} />
                    </div>
                  </div>
                }
                <Divider className={classes.divider} variant="fullWidth" />
                <CommentSection playgroundId={this.state.playground.id} />
              </CardContent>
            </Card>
          </div>
          {/* modals */}
          {this.state.loggedIn &&
            <div id="modal">
              <ReportPlaygroundModal playground={this.state.playground} />
              <AddCommentModal playground={this.state.playground} />
              <ReportCommentModal />
            </div>
          }
        </MuiThemeProvider>
      );
    }
  }
}

// global state mapping in Playground.js props
const mapStateToProps = (state) => {
  return {
    openReportPlayground: state.toggleModal.openReportPlayground,
  }
}

export default connect(mapStateToProps)(withStyles(styles)(Playground));