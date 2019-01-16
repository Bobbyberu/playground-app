import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { createMuiTheme } from '@material-ui/core/styles';
import grey from '@material-ui/core/colors/grey';
import Home from './pages/home/home';
import Playground from './pages/playground/Playground';
import Login from './pages/login/login';
import SignUp from './pages/sign-up/sign-up';
import NotFound from './pages/404/NotFound';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: grey[700],
    },
    secondary: {
      main: grey[50],
    },
  },
  overrides: {
    MuiInputBase: {
      root: {
        borderRadius: '4px',
        background: '#fff',
        backgroundColor: '#fff',
      },
    },
    MuiFormHelperText: {
      contained: {
        position: 'absolute',
        margin: 0,
        marginLeft: 10,
        marginTop: 80,
      },
    },
    textvalidatorDate: {
      MuiFormHelperText: {
        contained: {
          marginTop: 80,
        },
      },
    },
  },
});

function AppRouter() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={() => (<Home />)} />
        <Route path="/details/:id" component={Child} />
        <Route path="/login" component={() => (<Login theme={theme} />)} />
        <Route path="/signup" component={() => (<SignUp theme={theme} />)} />
        <Route component={NoMatch} />
      </Switch>
    </Router>
  );
}

function Child({ match }) {
  return (
    <div className="playground-page">
      <Playground id={match.params.id} />
    </div>
  );
}

function NoMatch({ location }) {
  return (<NotFound path={location.pathname} />);
}

export default AppRouter;
