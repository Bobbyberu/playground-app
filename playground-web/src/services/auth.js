import decode from 'jwt-decode';
import axios from 'axios';

export default class AuthService {
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
                // setting the token in local storage 
                this.setToken(response.headers['authorization']);
                return Promise.resolve(response);
            });
    }

    loggedIn() {
        // getting token from localstorage
        const token = this.getToken();
        // !! -> false if token empty
        // checks if there is a saved token and it's still valid
        return !!token && !this.isTokenExpired(token);
    }

    // Récuperer les informations du user avec son mail
    getUser(mail) {

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
        // retrieves the user token from localStorage
        return localStorage.getItem('token')
    }

    setToken(token) {
        localStorage.setItem('token', token);
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem('token');
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }
}