import AuthService from './auth';

const axios = require('axios');
const api = 'http://localhost:8080/api/';
const authService = new AuthService();

export default class PlaygroundAPI {
  getAllPlayground() {
    return axios({
      method: 'get',
      url: api + 'playgrounds/',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data);
  }

  getSearchResult(searchTerm) {
    return axios({
      method: 'get',
      url: api + 'playgrounds/search/' + searchTerm,
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data);
  }

  getAllSports() {
    return axios.get('/sports.json')
      .then(response => response.data.result);
  }

  getPlaygroundById(id) {
    return axios({
      method: 'get',
      //url: '/playgrounds.' + id + '.json',
      url: api + 'playgrounds/' + id,
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(response => response.data)
      .catch(err => {
        console.log(err);
      });
  }

  getUserById(id) {
    return axios({
      method: 'get',
      url: api + 'users/' + id,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }


  getUser(username) {
    return axios({
      method: 'get',
      url: api + 'users/name/' + username,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  getUserByMail(mail) {
    return axios({
      method: 'get',
      url: api + 'users/mail/' + mail,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  updateUser(user) {
    return axios({
      method: 'put',
      url: api + 'users/'  + user.id,
      data: user,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
      .catch(err => console.log(err));
  }

  getAllComments(playgroundId) {
    return axios({
      method: 'get',
      //url: '/comments.json',
      url: api + 'playgrounds/' + playgroundId + '/comments',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data)
      .catch(err => {
        console.log(err);
      });
  }

  postComment(comment) {
    return axios({
      method: 'post',
      url: api + 'comments',
      data: comment,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
      .catch(err => console.log(err));
  }
}
