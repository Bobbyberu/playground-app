import React, { Component } from 'react';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import grey from '@material-ui/core/colors/grey';
import TextField from '@material-ui/core/TextField';
import './sign-up.css';

const theme = createMuiTheme({
    palette: {
        primary: {
            main: grey[700]
        },
        secondary: {
            main: grey[700]
        }
    }
});

const style = {
    textfield: {
        background: '#fff'
    }
}

export default class SignUp extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            nickname: '',
            birthYear: 1970,
            birthMonth: 1,
            birthDay: 1,
            password: '',
            confirmPassword: '',
            termOfUse: false
        }

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleSubmit(event) {

    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    render() {
        console.log(this.state);
        return (
            <MuiThemeProvider theme={theme}>
                <div>
                    <h1>SignUp</h1>

                    <form onSubmit={this.handleSubmit}>
                        <TextField
                            id="email-input"
                            label="Adresse mail"
                            type="email"
                            name="email"
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleInputChange}
                            style={style.textfield}
                        />

                        <TextField
                            id="nickname-input"
                            label="Pseudo"
                            type="nickname"
                            name="nickname"
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleInputChange}
                        />

                        <TextField
                            id="password-input"
                            label="Mot de passe"
                            type="password"
                            name="password"
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleInputChange}
                        />

                        <TextField
                            id="confirm-password-input"
                            label="Confirmation de mot passe"
                            type="confirmPassword"
                            name="confirmPassword"
                            margin="normal"
                            variant="outlined"
                            onChange={this.handleInputChange}
                        />
                    </form>
                </div>
            </MuiThemeProvider>
        )
    }
}