import React from 'react';
import PropTypes from 'prop-types';
import { withStyles, MuiThemeProvider } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Avatar from '../../pictures/bob.PNG';

const styles = theme => ({
  media: {
    width: theme.spacing.unit * 20,
    height: theme.spacing.unit * 20,
    margin: theme.spacing.unit * 2,
    borderRadius: 1000,
    display: 'inline-block',
  },
});

function sumUp(props) {
  const { classes } = props;
  return (
    <Card className={classes.card}>
      <CardActionArea>
        <CardMedia
          className={classes.media}
          image={Avatar}
          title="Avatar"
        />
        <CardContent classname={classes.container} >
          <Typography gutterBottom variant="h6" component="h2">
            {props.username}
          </Typography>
          <Typography >
            {props.birthDate}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

sumUp.propTypes = {
  classes: PropTypes.object.isRequired,
  username: PropTypes.string.isRequired,
  birthDate: PropTypes.string.isRequired,
};

export default withStyles(styles)(sumUp);