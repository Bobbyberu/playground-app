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

function renderDate(date) {
  let newDate = new Date(date);
  let day = ('0' + newDate.getDay()).slice(-2);
  let month = ('0' + newDate.getMonth()).slice(-2);
  return day + "/" + month + "/" + newDate.getFullYear();
}

function sumUp(props) {
  const { classes } = props;
  return (
    <Card className={classes.card}>
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
          {'Date de naissance : ' + renderDate(props.birthDate)}
        </Typography>
      </CardContent> 
    </Card>
  );
}

sumUp.propTypes = {
  classes: PropTypes.object.isRequired,
  username: PropTypes.string.isRequired,
  birthDate: PropTypes.string.isRequired,
};

export default withStyles(styles)(sumUp);