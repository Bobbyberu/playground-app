import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Avatar from '../../pictures/bob.PNG';

const styles = {
    media: {
        width: 150,
        height: 150,
        borderRadius: 1000,
        horizontalAlign: 'center'
    }
};

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
        <CardContent>
          <Typography gutterBottom variant="h6" component="h2">
            15/02/1996
          </Typography>
          <Typography component="p">
            Bonjour je m'appelle Aubien mais vous pouvez m'appelez Kali Bouc Becker akai l'impulsif.
            J'aime beaucoup le choux rouge, les licornes et les pâtes au pesto.
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

sumUp.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(sumUp);