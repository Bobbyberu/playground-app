import React from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

import Avatar from '@material-ui/core/Avatar';
import Card from '@material-ui/core/Card';
import IconButton from '@material-ui/core/IconButton';

import AuthService from '../../../../services/auth';
import PlaygroundAPI from '../../../../services/playground-api';
import StarRating from '../StarRating';

import { withStyles } from '@material-ui/core/styles';
import avatar from '../../../../assets/img/default_avatar.png';
import Delete from '@material-ui/icons/Delete';
import Flag from '@material-ui/icons/Flag';
import green from '@material-ui/core/colors/green';

const styles = {
    bigAvatar: {
        margin: 10,
        width: 60,
        height: 60,
        border: '4px solid' + green[500]
    },
    comment: {
        textAlign: 'justify'
    },
    user: {
        display: 'inherit'
    },
    username: {
        marginTop: 'auto',
        marginBottom: 'auto',
        marginLeft: 10
    },
    rating: {
        marginTop: 'auto',
        marginBottom: 'auto',
    },
    report: {
        float: 'right'
    }
}

class Comment extends React.Component {
    state = ({
        redirect: false
    })

    toggleReportComment = () => {
        let value = {
            open: true,
            comment: this.props.comment
        };
        // Action will be sent to store
        const action = { type: "TOGGLE_REPORT_COMMENT", value: value }
        this.props.dispatch(action)
    }

    deleteComment = () => {
        let confirm = window.confirm("Voulez-vous vraiment supprimer ce commentaire ?");
        if (confirm) {
            PlaygroundAPI.deleteComment(this.props.comment.id)
                .then(() => window.location.reload())
                .catch(err => console.log(err));
        }
    }

    render() {
        if (this.state.redirect) {
            return (
                <React.Fragment>
                    <Redirect to="/" />
                </React.Fragment>
            );
        }
        const { classes } = this.props;
        let comment = this.props.comment;
        let author = comment.author;

        // if user got no avatar select application default avatar instead
        let avatarSrc = author.avatar ? author.avatar : avatar;

        return (
            <Card elevation={5}>
                <div>
                    <div className="row col-12 justify-content-between">
                        <div className={classes.user}>
                            <Avatar alt={author.username} src={avatarSrc} className={classes.bigAvatar} />
                            <p className={classes.username}>{author.username}</p>
                        </div>
                        <div className={classes.rating}>
                            <StarRating rating={comment.mark} number={false} />
                        </div>
                    </div>
                    <p className={classes.comment + ' col-12'}>{comment.comment}</p>
                    {AuthService.isUser() &&
                        <IconButton className={classes.report} onClick={this.toggleReportComment}>
                            <Flag color="primary" />
                        </IconButton>
                    }

                    {AuthService.isAdmin() &&
                        <IconButton className={classes.report} onClick={this.deleteComment}>
                            <Delete color="primary" />
                        </IconButton>
                    }
                </div>
            </Card>
        );
    }
}

// global state mapping in Playground.js props
const mapStateToProps = (state) => {
    return {
        openReportComment: state.toggleModal.openReportComment
    }
}

export default connect(mapStateToProps)(withStyles(styles)(Comment));