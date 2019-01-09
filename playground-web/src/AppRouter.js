import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import grey from '@material-ui/core/colors/grey';
import Home from './pages/home/home';
import PlaygroundNew from './pages/playground-new/playground-new';
import Login from './pages/login/login';
import SignUp from './pages/sign-up/sign-up';

const theme = createMuiTheme({
    palette: {
        primary: {
            main: grey[700]
        },
        secondary: {
            main: grey[50]
        }
    },
    overrides: {
        MuiInputBase: {
            root: {
                borderRadius: '4px',
                background: '#fff',
                backgroundColor: '#fff',
            }
        },
        MuiFormHelperText: {
            contained: {
                position: 'absolute',
                margin: 0,
                marginLeft: 10,
                marginTop: 80
            }
        },
        textvalidatorDate: {
            MuiFormHelperText: {
                contained: {
                    marginTop: 80
                }
            }
        }
    }
});

const AppRouter = () => (
    <React.Fragment>
        <Route path="/" exact component={() => (<Home />)} />
        <Route path="/details" component={() => (<PlaygroundNew />)} />
        <MuiThemeProvider theme={theme}>
            <Route path="/login" component={() => (<Login />)} />
            <Route path="/signup" component={() => (<SignUp />)} />
        </MuiThemeProvider>
    </React.Fragment>
);

export default AppRouter;