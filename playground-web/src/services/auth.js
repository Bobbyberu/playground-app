import decode from 'jwt-decode';
import axios from 'axios';

class Authentication {
    constructor(domain) {
        this.domain = domain || 'http://localhost:8080/api/users/';
        this.login = this.login.bind(this);
    }

    signUp(user) {
        return axios({
            method: 'post',
            url: this.domain + 'signup',
            data: user,
            headers: { 'Content-Type': 'application/json' }
        })
            .catch(err => console.log(err));
    }

    login(mail, password) {
        // get a token from api server
        return axios({
            method: 'post',
            url: this.domain + 'login',
            data: { 'mail': mail, 'password': password },
            headers: { 'Content-Type': 'application/json' }
        })
            .then((response) => {
                // setting the token in session storage 
                this.setToken(response.headers['authorization']);
                return Promise.resolve(response);
            });
    }

    loggedIn() {
        // getting token from sessionstorage
        const token = this.getToken();
        // !! -> false if token empty
        // checks if there is a saved token and it's still valid
        return !!token && !this.isTokenExpired(token);
    }

    isUser() {
        return AuthService.loggedIn() &&
            AuthService.getUser().role === 'ROLE_USER';
    }

    isAdmin() {
        return AuthService.loggedIn() &&
            AuthService.getUser().role === 'ROLE_ADMIN';
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);

            // checking if token is expired
            if (decoded.exp < Date.now() / 1000) {
                return true;
            }
            else
                return false;
        }
        catch (err) {
            return false;
        }
    }

    decodeToken() {
        return decode(this.getToken());
    }

    getToken() {
        // retrieves the user token from sessionStorage
        return sessionStorage.getItem('token');
    }

    getUser() {
        return JSON.parse(sessionStorage.getItem('user'));
    }

    setToken(token) {
        sessionStorage.setItem('token', token);
    }

    setUser(user) {
        let newUser = { "id": user.id, "username": user.username, "mail": user.mail, "role": user.role.name }
        sessionStorage.setItem('user', JSON.stringify(newUser));
    }

    logout() {
        // Clear user token and profile data from sessionStorage
        sessionStorage.removeItem('token');
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }
};

var AuthService = new Authentication();

export default AuthService;