import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

import FavoriteIcon from '@material-ui/icons/StarBorder';
import IconButton from '@material-ui/core/IconButton';

const styles = {
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
    },
    subtitle: {
        fontStyle: 'italic',
        fontSize: 12,
        marginTop: 0,
    }
};

const PlaygroundDetails = ({ playground, classes }) => (
    <div className="global">
        <Card className={classes.card}>
            <CardMedia
                className={classes.media}
                image={playground.image}
                title="Photo du playground"
            />
            <CardContent>
                <Typography className={classes.title} gutterBottom variant="h5" component="h2">
                    {playground.name}
                </Typography>
                <Typography className={classes.subtitle} component="h3">
                    {playground.address}
                </Typography>
            </CardContent>
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
)

PlaygroundDetails.propTypes = {
    playground: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PlaygroundDetails)