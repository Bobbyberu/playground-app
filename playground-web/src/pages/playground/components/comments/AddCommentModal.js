import React from "react";
import { connect } from "react-redux";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import FormControl from "@material-ui/core/FormControl";
import MenuItem from "@material-ui/core/MenuItem";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import Select from "@material-ui/core/Select";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";

import AuthService from "../../../../services/auth";
import PlaygroundAPI from "../../../../services/playground-api";

import { withStyles } from "@material-ui/core/styles";

const styles = {
  global: {
    padding: 40,
    paddingBottom: 10
  },
  selectMark: {
    background: "#fff",
    borderRadius: "4px",
    width: 150,
    marginTop: 20,
    marginBottom: 40
  }
};

class AddCommentModal extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      mark: 0,
      comment: "",
      completed: false
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  async handleSubmit() {
    let user = AuthService.getUser();

    let comment = {
      playground: this.props.playground,
      author: user,
      archived: false,
      comment: this.state.comment,
      mark: this.state.mark
    };

    PlaygroundAPI.postComment(comment).then(() => {
      this.setState({
        completed: true
      });
    });
  }

  handleClose() {
    // Action à envoyer au store
    const action = { type: "TOGGLE_ADD_COMMENT", value: false };
    this.props.dispatch(action);
  }

  handleEnter() {
    // Triggers whenever modal is opened
    this.setState({
      mark: 0,
      comment: "",
      completed: false
    });
  }

  render() {
    const { classes, open } = this.props;

    const content = this.state.completed
      ? this.renderCompleted()
      : this.renderForm(classes);

    return (
      <Dialog
        open={open}
        onEnter={this.handleEnter.bind(this)}
        onClose={this.handleClose.bind(this)}
        maxWidth={"md"}
        fullWidth
      >
        <div className={classes.global}>{content}</div>
      </Dialog>
    );
  }

  getMarks() {
    let marks = [];

    for (let i = 0; i < 6; i++) {
      marks.push(i);
    }

    return marks;
  }

  renderForm(classes) {
    let marks = this.getMarks();
    return (
      <React.Fragment>
        <DialogTitle>
          <span role="img" aria-label="note">
            📝
          </span>{" "}
          Rédiger un avis
        </DialogTitle>
        <ValidatorForm
          ref="form"
          onSubmit={this.handleSubmit}
          onError={errors => console.log(errors)}
        >
          <DialogContent>
            <div>
              <DialogContentText>
                Veuillez renseigner une note de 0 à 5.
              </DialogContentText>
              <FormControl variant="outlined" className={classes.selectMark}>
                <Select
                  value={this.state.mark}
                  onChange={this.handleInputChange}
                  input={
                    <OutlinedInput
                      labelWidth={0}
                      name="mark"
                      id="outlined-age-simple"
                    />
                  }
                  displayEmpty
                >
                  {marks.map(index => (
                    <MenuItem key={index} value={index}>
                      {index.toString()}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </div>
            <TextValidator
              multiline
              fullWidth
              onChange={this.handleInputChange}
              name="comment"
              rows="4"
              placeholder="Partagez votre expérience concernant ce playground"
              value={this.state.comment}
              variant="outlined"
              validators={["required"]}
              errorMessages={["Champ obligatoire"]}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="primary">
              Annuler
            </Button>
            <Button type="submit" color="primary">
              Publier
            </Button>
          </DialogActions>
        </ValidatorForm>
      </React.Fragment>
    );
  }

  renderCompleted() {
    return (
      <React.Fragment>
        <DialogContent>Votre avis a bien été envoyé !</DialogContent>
        <DialogActions>
          <Button onClick={this.handleClose} color="primary">
            OK
          </Button>
        </DialogActions>
      </React.Fragment>
    );
  }
}

// global state mapping in Playground.js props
const mapStateToProps = state => {
  return {
    open: state.toggleModal.openAddComment
  };
};

export default connect(mapStateToProps)(withStyles(styles)(AddCommentModal));
