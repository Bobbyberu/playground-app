import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';

import InputAdornment from '@material-ui/core/InputAdornment';
import Button from '@material-ui/core/Button';
import { MuiThemeProvider } from '@material-ui/core/styles';
import { withStyles } from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';

import MailOutline from '@material-ui/icons/MailOutline';
import VpnKey from '@material-ui/icons/VpnKey';

import AuthService from '../../services/auth';
import PlaygroundAPI from '../../services/playground-api';
import './login.css';

const styles = ({
  title: {
    color: '#fff',
    marginTop: 50,
  },
  text: {
    color: '#fff',
  },
  container: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translateX(-50%) translateY(-50%)',
    backgroundColor: 'rgba(0,0,0,0.7)',
    borderRadius: 10,
    width: 700,
    height: '60%',
  },
  textvalidator: {
    width: 450,
    margin: 30,
  },
  input: {
    fontSize: '1.5em',
    marginLeft: 10,
  },
  iconInput: {
    color: '#000',
  },
  button: {
    backgroundColor: green[900],
    color: '#fff',
    margin: 50,
    marginBottom: 15,
    paddingTop: 15,
    paddingBottom: 15,
    paddingLeft: 20,
    paddingRight: 20,
    '&:hover': {
      backgroundColor: green[800],
    },
  },
});

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mail: '',
      password: '',
      redirect: false
    }
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async handleSubmit(event) {
    event.preventDefault();
    let loginSuccess = false;

    await AuthService.login(this.state.mail, this.state.password)
      .then(() => loginSuccess = true)
      .catch(err => {
        console.log(err);
      });

    if (loginSuccess) {
      await PlaygroundAPI.getUser(this.state.mail)
        .then(response => {
          AuthService.setUser(response);
          this.setState({
            redirect: true
          });
        })
        .catch(err => {
          console.log(err);
        });
    }
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState({
      [name]: value,
    });
  }

  render() {
    const content = () => {
      if (AuthService.loggedIn()) {
        return this.redirect()
      } else {
        return this.state.redirect ? this.redirect() : this.renderForm(this.props);
      }
    };
    return (

      <MuiThemeProvider theme={this.props.theme}>
        {content()}
      </MuiThemeProvider>
    );
  }

  redirect() {
    return <Redirect to="/" />;
  }

  renderForm(props) {
    const { classes } = props;
    return (
      <div className={`${classes.container} container`} id="login-container">
        <h1 className={classes.title}>Login</h1>

        <ValidatorForm
          className={classes.form}
          ref="form"
          onSubmit={this.handleSubmit}
          onError={errors => console.log(errors)}
        >
          <div className="row">
            <div className="col-12">
              <TextValidator
                className={classes.textvalidator}
                onChange={this.handleInputChange}
                name="mail"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <MailOutline color="primary" />
                    </InputAdornment>
                  ),
                  classes: { input: classes.input }
                }}
                placeholder="Email"
                value={this.state.mail}
                variant="outlined"
                validators={['required', 'isEmail']}
                errorMessages={['Champ obligatoire', 'L\' adresse mail n\'est pas valide']}
              />
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
                  classes: { input: classes.input },
                }}
                placeholder="Mot de passe"
                value={this.state.password}
                variant="outlined"
                type="password"
                validators={['required']}
                errorMessages={['Champ obligatoire']}
              />
            </div>

            <div className="col-12">
              <Button type="submit" color="secondary" variant="contained" className={classes.button}>Valider</Button>
            </div>

            <div className={`${classes.text} col-12`}>
              Pas encore inscrit ?
              {' '}
              <Link to="/signup">Inscrivez-vous !</Link>
            </div>
          </div>
        </ValidatorForm>
      </div>
    );
  }
}

export default withStyles(styles)(Login);
