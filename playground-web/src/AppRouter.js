import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { createMuiTheme } from "@material-ui/core/styles";
import grey from "@material-ui/core/colors/grey";
import CommentReports from "./pages/admin/CommentReports";
import Home from "./pages/home/home";
import Playground from "./pages/playground/Playground";
import PlaygroundReports from "./pages/admin/PlaygroundReports";
import Login from "./pages/login/login";
import SignUp from "./pages/sign-up/sign-up";
import NotFound from "./pages/404/NotFound";
import Profile from "./pages/profile/profile";
import Test from "./pages/test";

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
        borderRadius: "4px",
        background: "#fff",
        backgroundColor: "#fff"
      }
    },
    MuiFormHelperText: {
      contained: {
        position: "absolute",
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
    },
    MuiExpansionPanel: {
      root: {
        marginBottom: 10
      }
    }
  }
});

function AppRouter() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={() => <Home />} />
        <Route path="/details/:id" component={Child} />
        <Route path="/login" component={() => <Login theme={theme} />} />
        <Route path="/signup" component={() => <SignUp theme={theme} />} />
        <Route path="/profile" component={() => <Profile theme={theme} />} />
        <Route path="/test" component={() => <Test />} />
        <Route
          path="/report/playgrounds"
          component={() => <PlaygroundReports />}
        />
        <Route path="/report/comments" component={() => <CommentReports />} />
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
  return <NotFound path={location.pathname} />;
}

export default AppRouter;
