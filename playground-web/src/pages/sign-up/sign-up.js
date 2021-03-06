import React, { Component } from "react";
import { Link } from "react-router-dom";
import green from "@material-ui/core/colors/green";
import red from "@material-ui/core/colors/red";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import Checkbox from "@material-ui/core/Checkbox";
import Button from "@material-ui/core/Button";
import MailOutline from "@material-ui/icons/MailOutline";
import ErrorOutline from "@material-ui/icons/ErrorOutline";
import Person from "@material-ui/icons/Person";
import VpnKey from "@material-ui/icons/VpnKey";
import DateRange from "@material-ui/icons/DateRange";
import InputAdornment from "@material-ui/core/InputAdornment";
import { withStyles } from "@material-ui/core/styles";
import { ValidatorForm, TextValidator } from "react-material-ui-form-validator";
import AuthService from "../../services/auth";
import "./sign-up.css";
import { MuiThemeProvider } from "@material-ui/core/styles";

const styles = {
  title: {
    color: "#fff",
    marginTop: 30,
    marginBottom: 20
  },
  text: {
    color: "#fff"
  },
  container: {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translateX(-50%) translateY(-50%)",
    backgroundColor: "rgba(0,0,0,0.7)",
    borderRadius: 10,
    width: 800
  },
  textvalidator: {
    width: 450,
    margin: 20
  },
  rowCentered: {
    width: 450,
    marginLeft: "auto",
    marginRight: "auto"
  },
  textfieldDate: {
    background: "#fff",
    borderRadius: "4px",
    width: 125,
    marginTop: 30,
    marginBottom: 30
  },
  textvalidatorDate: {
    width: 125,
    marginTop: 30,
    marginBottom: 30
  },
  selectMonth: {
    background: "#fff",
    borderRadius: "4px",
    width: 150,
    marginTop: 30,
    marginBottom: 30,
    marginLeft: 25,
    marginRight: 25
  },
  input: {
    fontSize: "1.5em",
    marginLeft: 10
  },
  inputDate: {
    fontSize: "20px",
    p: {
      marginTop: 2
    }
  },
  iconInput: {
    color: "#000"
  },
  button: {
    backgroundColor: green[900],
    color: "#fff",
    margin: 50,
    paddingTop: 15,
    paddingBottom: 15,
    paddingLeft: 20,
    paddingRight: 20,
    "&:hover": {
      backgroundColor: green[800]
    }
  },
  checkboxError: {
    color: red[600]
  }
};

class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      username: "",
      birthYear: "",
      birthMonth: 0,
      birthDay: "",
      password: "",
      confirmation: "",
      termsOfUse: false,
      termsOfUseHasBeenValidated: false,
      // sign up is over and validated
      finalized: false
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleErrors = this.handleErrors.bind(this);
  }

  componentWillMount() {
    // custom rule will have name 'isPasswordMatch'
    ValidatorForm.addValidationRule("isPasswordMatch", value => {
      if (value !== this.state.password) {
        return false;
      }
      return true;
    });
  }

  handleSubmit(event) {
    event.preventDefault();

    this.setState({
      termsOfUseHasBeenValidated: true
    });

    // to avoid UTC time converion by server
    let birthDate = new Date(
      Date.UTC(this.state.birthYear, this.state.birthMonth, this.state.birthDay)
    );
    let user = {
      username: this.state.username,
      mail: this.state.email,
      password: this.state.password,
      birthDate: birthDate
    };

    AuthService.signUp(user)
      .then(() => {
        this.setState({
          finalized: true
        });
      })
      .catch(err => console.log(err));
  }

  handleErrors(errors) {
    this.setState({
      termsOfUseHasBeenValidated: true
    });
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === "checkbox" ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  errorCheckbox() {
    const { classes } = this.props;

    if (
      (!this.state.termsOfUse && !this.state.termsOfUseHasBeenValidated) ||
      this.state.termsOfUse
    ) {
      return null;
    }

    return (
      <div className={classes.checkboxError}>
        <ErrorOutline /> Veuillez accepter les CGU
      </div>
    );
  }

  render() {
    const content = this.state.finalized
      ? this.renderFinalized(this.props)
      : this.renderForm(this.props);
    return (
      <MuiThemeProvider theme={this.props.theme}>{content}</MuiThemeProvider>
    );
  }

  renderFinalized(props) {
    const { classes } = props;
    return (
      <div className={`${classes.text} container`}>
        <div className="col-12">Votre Inscription est terminée !</div>
        <div className="col-12">
          Un email de confirmation va vous être envoyé.
        </div>
        <div className="col-12">
          <Link to="/login">Retourner à la page de connexion</Link>
        </div>
      </div>
    );
  }

  renderForm(props) {
    const { classes } = props;
    return (
      <div className={`${classes.container} container`}>
        <h1 className={classes.title}>Inscription</h1>

        <ValidatorForm
          ref="form"
          onSubmit={this.handleSubmit}
          onError={errors => this.handleErrors(errors)}
        >
          <div className="row">
            <div className="col-12">
              <TextValidator
                className={classes.textvalidator}
                onChange={this.handleInputChange}
                name="email"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <MailOutline color="primary" />
                    </InputAdornment>
                  ),
                  classes: { input: classes.input }
                }}
                placeholder="Email"
                value={this.state.email}
                variant="outlined"
                validators={["required", "isEmail"]}
                errorMessages={[
                  "Champ obligatoire",
                  "L' adresse mail n'est pas valide"
                ]}
              />
            </div>

            <div className="col-12">
              <TextValidator
                className={classes.textvalidator}
                onChange={this.handleInputChange}
                name="username"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <Person color="primary" />
                    </InputAdornment>
                  ),
                  classes: { input: classes.input }
                }}
                placeholder="Pseudo"
                value={this.state.username}
                variant="outlined"
                validators={["required", "minStringLength: 5"]}
                errorMessages={[
                  "Champ obligatoire",
                  "Votre pseudo doit être long d'au moins 5 caractères"
                ]}
              />
            </div>

            <div className="col-12">
              <div className={`${classes.rowCentered} row`}>
                <div className="dateValidator">
                  <TextValidator
                    className={classes.textvalidatorDate}
                    onChange={this.handleInputChange}
                    name="birthDay"
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <DateRange color="primary" />
                        </InputAdornment>
                      )
                    }}
                    placeholder="Jour"
                    value={this.state.birthDay}
                    variant="outlined"
                    type="number"
                    validators={["required", "minNumber:1", "maxNumber:31"]}
                    errorMessages={[
                      "Champ obligatoire",
                      "Valeur inférieure à 1",
                      "Valeur supérieure à 31"
                    ]}
                  />
                </div>
                <div>
                  <FormControl
                    variant="outlined"
                    className={classes.selectMonth}
                  >
                    <Select
                      value={this.state.birthMonth}
                      onChange={this.handleInputChange}
                      input={
                        <OutlinedInput
                          labelWidth={0}
                          name="birthMonth"
                          id="outlined-age-simple"
                        />
                      }
                      displayEmpty
                    >
                      <MenuItem value={0}>janvier</MenuItem>
                      <MenuItem value={1}>février</MenuItem>
                      <MenuItem value={2}>mars</MenuItem>
                      <MenuItem value={3}>avril</MenuItem>
                      <MenuItem value={4}>mai</MenuItem>
                      <MenuItem value={5}>juin</MenuItem>
                      <MenuItem value={6}>juillet</MenuItem>
                      <MenuItem value={7}>août</MenuItem>
                      <MenuItem value={8}>septembre</MenuItem>
                      <MenuItem value={9}>octobre</MenuItem>
                      <MenuItem value={10}>novembre</MenuItem>
                      <MenuItem value={11}>décembre</MenuItem>
                    </Select>
                  </FormControl>
                </div>

                <div className="dateValidator">
                  <TextValidator
                    className={classes.textvalidatorDate}
                    onChange={this.handleInputChange}
                    name="birthYear"
                    placeholder="Année"
                    value={this.state.birthYear}
                    variant="outlined"
                    type="number"
                    validators={["required", "minNumber:0"]}
                    errorMessages={[
                      "Champ obligatoire",
                      "Votre valeur doit être positive"
                    ]}
                  />
                </div>
              </div>
            </div>

            <div className="col-12">
              <TextValidator
                className={classes.textvalidator}
                onChange={this.handleInputChange}
                name="password"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <VpnKey color="primary" />
                    </InputAdornment>
                  ),
                  classes: { input: classes.input }
                }}
                placeholder="Mot de passe"
                value={this.state.password}
                variant="outlined"
                type="password"
                validators={[
                  "required",
                  "matchRegexp:^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
                ]}
                errorMessages={[
                  "Champ obligatoire",
                  "Votre mot de passe doit contenir au moins 8 caractères, une minuscule, une majuscule, un chiffre et un caractère spécial"
                ]}
              />
            </div>

            <div className="col-12">
              <TextValidator
                className={classes.textvalidator}
                onChange={this.handleInputChange}
                name="confirmation"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <VpnKey color="primary" />
                    </InputAdornment>
                  ),
                  classes: { input: classes.input }
                }}
                placeholder="Confirmation mot de passe"
                type="password"
                value={this.state.confirmation}
                variant="outlined"
                validators={["required", "isPasswordMatch"]}
                errorMessages={[
                  "Champ obligatoire",
                  "Vos mots de passe ne correspondent pas"
                ]}
              />
            </div>
          </div>

          <div className="col-12">
            <div className={classes.rowCentered}>
              <Checkbox
                name="termsOfUse"
                checked={this.state.termsOfUse}
                onChange={this.handleInputChange}
                style={{ color: "#fff" }}
              />
              <span className={classes.text}>
                J'accepte les conditions d'utilisation
              </span>
              {this.errorCheckbox()}
            </div>
          </div>

          <div className="col-12">
            <Button
              type="submit"
              color="secondary"
              variant="contained"
              className={classes.button}
            >
              Valider
            </Button>
          </div>
        </ValidatorForm>
      </div>
    );
  }
}

export default withStyles(styles)(SignUp);
