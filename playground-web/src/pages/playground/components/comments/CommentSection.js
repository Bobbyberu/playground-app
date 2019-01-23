import React from 'react'
import { connect } from 'react-redux';

import Fab from '@material-ui/core/Fab';

import AuthService from '../../../../services/auth';
import Comment from './Comment';
import PlaygroundAPI from '../../../../services/playground-api';

import { withStyles } from '@material-ui/core/styles';
import AddComment from '@material-ui/icons/AddComment';

const styles = {
    sectionTitle: {
        textAlign: 'left',
        marginTop: 10,
        marginBottom: 40
    },
    comment: {
        marginBottom: 20,
    },
    commentSection: {
        margin: 0,
        padding: 0
    },
    topRow: {
        marginRight: 0,
        paddingRight: 0
    },
    addIcon: {
        marginRight: 10,
    }
}

class CommentSection extends React.Component {
    constructor(props) {
        super(props);

        this.state = ({
            comments: 0,
            loggedIn: false
        })
        this.toggleModal = this.toggleModal.bind(this);
    }

    componentDidMount() {
        PlaygroundAPI.getAllComments(this.props.playgroundId)
            .then(response => {
                this.setState({
                    comments: response,
                    loggedIn: AuthService.loggedIn()
                });
            })
            .catch(err => console.log(err));
    }

    toggleModal() {
        // Action will be sent to store
        const action = { type: "TOGGLE_ADD_COMMENT", value: true }
        this.props.dispatch(action)
    }

    render() {
        const { classes } = this.props;
        let comments = this.state.comments;
        let commentsDisplay;
        if (comments === 0) {
            commentsDisplay = <div><h2>Chargement mon petit pote</h2></div>;
        } else {
            commentsDisplay = comments.map(comment => (
                <div className={classes.comment + ' col-6'} key={comment.id}>
                    <Comment comment={comment} canReport={this.state.loggedIn} />
                </div>
            ));
        }

        return (
            <div id="comment-section">
                <div className={classes.topRow + ' row col-12 justify-content-between'}>
                    <div className={classes.sectionTitle}><h5>📝 Avis</h5></div>
                    {this.state.loggedIn &&
                        <div>
                            <Fab variant="extended" color="primary" onClick={this.toggleModal}>
                                <AddComment className={classes.addIcon} />Rédiger un avis
                        </Fab>
                        </div>
                    }
                </div>
                <div className="row col-12">
                    {commentsDisplay}
                </div>
            </div>
        );
    }
}

// global state mapping in Playground.js props
const mapStateToProps = (state) => {
    return {
        open: state.toggleModal.openAddComment
    }
}

export default connect(mapStateToProps)(withStyles(styles)(CommentSection));