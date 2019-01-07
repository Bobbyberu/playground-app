import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Home from './pages/home/home';
import PlaygroundNew from './pages/playground-new/playground-new';
import SignIn from './pages/sign-in/sign-in';
import SignUp from './pages/sign-up/sign-up';

const AppRouter = () => (
    <React.Fragment>
        <Route path="/" exact component={() => (<Home />)} />
        <Route path="/details" component={() => (<PlaygroundNew />)} />
        <Route path="/signin" component={() => (<SignIn />)} />
        <Route path="/signup" component={() => (<SignUp />)} />
    </React.Fragment>
);

export default AppRouter;