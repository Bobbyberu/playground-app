import AuthService from './auth';

const axios = require('axios');
const api = 'http://localhost:8080/api/';
const authService = new AuthService();

export default class PlaygroundAPI {
  getAllPlayground() {
    let token = authService.getToken();
    return axios({
      method: 'get',
      url: api + 'playgrounds/',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data);
  }

  getSearchResult(searchTerm) {
    let token = authService.getToken();
    return axios({
      method: 'get',
      url: api + 'playgrounds/search/' + searchTerm,
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data);
  }

  getRandomPlaygrounds() {
    return axios.get('/playgrounds.json')
      .then((response) => {
        const max = response.data.result.length;

        const start = Math.floor(Math.random() * (max / 2));
        const end = Math.floor((Math.random() * max) + max / 2);
        return response.data.result.slice(start, end);
      });
  }

  getAllSports() {
    return axios.get('/sports.json')
      .then(response => response.data.result);
  }

  postPlayground(playground) {
    let token = authService.getToken();
    return axios({
      method: 'post',
      url: api + 'playgrounds',
      data: playground,
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
      .catch(err => console.log(err));
  }
}
