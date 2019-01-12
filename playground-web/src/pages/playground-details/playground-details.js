import React from 'react';
import Emoji from '../../common-components/emoji/emoji';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import FavoriteIconEmpty from '@material-ui/icons/StarBorder';
import FavoriteIconFull from '@material-ui/icons/StarRate';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import Divider from '@material-ui/core/Divider';
import { connect } from 'react-redux';


// Override de certains éléments de la card 
const theme = createMuiTheme({
    overrides: {
        MuiListItem: {
            gutters: {
                paddingLeft: 0,
                paddingRight: 0,
            },
            root: {
                paddingTop: 0,
                paddingBottom: 0,
            }
        },
        MuiAvatar: {
            colorDefault: {
                backgroundColor: 'transparent',
            }
        },
        MuiCardContent: {
            root: {
                paddingBottom: 0,
            }
        },
        MuiTypography: {
            root: {
                paddingBottom: 0,
            }
        }
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
});

/*
 Contenu de la popup avec un element material ui Card
 */
class PlaygroundDetails extends React.Component {
    toggleFavorite = () => {
        // Action à envoyer au store
        const action = { type: "TOGGLE_FAVORITE", value: this.props.playground }
        this.props.dispatch(action)
    }

    displayFavoriteIcon = () => {
        // On modifie l'icône 'favori' en fonction de l'état du playground sélectionné
        const favoritePlaygrounds = (this.props.favoritePlaygrounds.findIndex(item => item.id === this.props.playground.id) !== -1)
        return (
            favoritePlaygrounds ? <FavoriteIconFull /> : <FavoriteIconEmpty />
        )
    }

    render() {
        const { classes } = this.props
        return (
            <MuiThemeProvider theme={theme}>
                <div className="global">
                    <Card className={classes.card}>
                        {/* Image associée au playground */}
                        <CardMedia
                            className={classes.media}
                            image={this.props.playground.image}
                            title="Photo du playground"
                        />
                        {/* Informations sur le playground*/}
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
                                    {this.props.playground.private ? "Privé" : "Public"}
                                </Typography>
                                <Divider variant="fullWidth" />
                            </div>
                            <div className={classes.section2}>
                                <Typography className={classes.p} component="p">
                                    {/* Récupération des sports d'un playground associés à leur emoji */}
                                    <List>
                                        {
                                            this.props.playground.sports.map((sport) => (
                                                <ListItem>
                                                    <Avatar className={classes.avatar}>
                                                        <Emoji symbol={sport.emoji} label={sport.name} />
                                                    </Avatar>
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
                            <IconButton aria-label="Ajouter aux favoris" onClick={() => this.toggleFavorite()}>
                                {this.displayFavoriteIcon()}
                            </IconButton>
                            <Button size="small" color="primary">
                                Détails
                            </Button>
                        </CardActions>
                    </Card>
                </div>
            </MuiThemeProvider>
        )
    }
}

// mapping du state global dans les props du composant PlaygroundDetails
const mapStateToProps = (state) => {
    return {
        favoritePlaygrounds: state.toggleFavorite.favoritePlaygrounds
    }
}

// La fonction mapStateToProps permet d'abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(PlaygroundDetails))