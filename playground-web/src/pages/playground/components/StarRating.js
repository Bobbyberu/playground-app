import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';
import StarFull from '@material-ui/icons/Star';
import StarHalf from '@material-ui/icons/StarHalf';
import StarEmpty from '@material-ui/icons/StarBorder';

const styles = {
    mark: {
        marginLeft: 30,
        marginTop: 2
    },
    stars: {
        color: green[500]
    }
}

class StarRating extends React.Component {
    renderStars() {
        const { classes } = this.props;
        var stars = [];
        let rating = this.props.rating;

        // build star rating display up from 1 to 5 stars
        for (let i = 1; i < 6; i++) {
            // if the rating is between n.75 and (n+1).4 then n stars should be full
            if (rating >= i || rating >= i - 0.25) {
                stars.push(<StarFull key={i} />);
            } else if (rating >= i - 0.6) { // if rating is more than n.4 then n-1 stars should be full with the nth stars half full
                stars.push(<StarHalf key={i} />);
            } else {
                stars.push(<StarEmpty key={i} />); // rating is less than n.4 then (n-1) stars should be full and the nth star is empty
            }
        }

        let starsRendering = stars.map(star => (star));

        return (
            <div className={classes.stars}>
                {starsRendering}
            </div>
        );
    }

    render() {
        const { classes } = this.props;
        return (
            <div className="row">
                {this.renderStars()}
                {this.props.number && // if true the mark will be displayed with a number
                    <p className={classes.mark}>{this.props.rating}</p>
                }
            </div>
        );
    }
}

export default withStyles(styles)(StarRating);