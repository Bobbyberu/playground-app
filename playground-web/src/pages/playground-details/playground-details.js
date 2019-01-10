import React from 'react';
import PropTypes from 'prop-types';
import Emoji from '../../common-components/emoji/emoji';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import FavoriteIcon from '@material-ui/icons/StarBorder';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import Divider from '@material-ui/core/Divider';

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
const PlaygroundDetails = ({ playground, classes }) => (
    <MuiThemeProvider theme={theme}>
        <div className="global">
            <Card className={classes.card}>
                {/* Image associée au playground */}
                <CardMedia
                    className={classes.media}
                    image={playground.image}
                    title="Photo du playground"
                />
                {/* Informations sur le playground*/}
                <CardContent>
                    <div className={classes.section1}>
                        <Typography className={classes.title} gutterBottom variant="h5">
                            {playground.name}
                        </Typography>
                        <Typography className={classes.subtitle} component="h3">
                            {playground.address}
                        </Typography>
                    </div>
                    <div>
                        <Typography className={classes.private} component="h3">
                            {playground.private ? "Privé" : "Public"}
                        </Typography>
                        <Divider variant="fullWidth" />
                    </div>
                    <div className={classes.section2}>
                        <Typography className={classes.p} component="p">
                            {/* Récupération des sports d'un playground associés à leur emoji */}
                            <List>
                                {
                                    playground.sports.map((sport) => (
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
                    <IconButton aria-label="Ajouter aux favoris">
                        <FavoriteIcon />
                    </IconButton>
                    <Button size="small" color="primary">
                        Détails
                    </Button>
                </CardActions>
            </Card>
        </div>
    </MuiThemeProvider>
)

PlaygroundDetails.propTypes = {
    playground: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PlaygroundDetails)