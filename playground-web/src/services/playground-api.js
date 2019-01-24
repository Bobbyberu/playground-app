import AuthService from './auth';

const axios = require('axios');
const api = 'http://localhost:8080/api/';

class API {
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
    return axios({
      method: 'get',
      url: api + 'sports/',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data);
  }

  getPlaygroundById(id) {
    return axios({
      method: 'get',
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
      url: api + 'users/mail/' + mail,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': AuthService.getToken()
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
      url: api + 'users/' + user.id,
      data: user,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authService.getToken()
      }
    })
  }

  postPlayground(playground) {
    let token = AuthService.getToken();
    return axios({
      method: 'post',
      url: api + 'playgrounds',
      data: playground,
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.data.id)
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
    let idPlayground = comment.playground.id;
    return axios({
      method: 'post',
      url: api + 'playgrounds/' + idPlayground + '/comments',
      data: comment,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': AuthService.getToken()
      }
    })
      .catch(err => console.log(err));
  }

  uploadImageUser(data) {
    return axios({
      method: 'post',
      url: api + 'users/image',
      data: data,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  getUserImage() {
    let userMail = AuthService.decodeToken().sub;
    return api + 'users/' + userMail + '/image';
  }

  uploadImagePlayground(id, data) {
    return axios({
      method: 'post',
      url: api + 'playgrounds/' + id + '/image',
      data: data,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  getPlaygroundImage(id) {
    return api + 'playgrounds/' + id + '/image';
  }

  isFavorite(user, playground) {
    return axios({
      method: 'get',
      url: api + 'users/' + user + '/favouritePlaygrounds/' + playground,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  updateFavorite(user, playground) {
    return axios({
      method: 'put',
      url: api + 'users/' + user + '/favouritePlaygrounds/' + playground,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': AuthService.getToken()
      }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  reportPlayground(user, playground, description) {
    let data = { "author": user, "playground": playground, "description": description }
    return axios({
      method: 'post',
      url: api + 'playgrounds/' + playground.id + '/reportPlaygrounds',
      data: data,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  reportComment(user, comment, description) {
    let data = { "author": user, "comment": comment, "description": description }
    return axios({
      method: 'post',
      url: api + 'comments/' + comment.id + '/reportComments',
      data: data,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  // admin endpoints

  deletePlayground(playgroundId) {
    return axios({
      method: 'delete',
      url: api + 'playgrounds/' + playgroundId,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  deleteComment(commentId) {
    return axios({
      method: 'delete',
      url: api + 'comments/' + commentId,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => console.log(err));
  }

  getAllPlaygroundReports() {
    return axios({
      method: 'get',
      url: api + 'reportPlaygrounds',
      headers: { 'Authorization': AuthService.getToken() }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  deletePlaygroundReport(reportId) {
    return axios({
      method: 'delete',
      url: api + 'reportPlaygrounds/' + reportId,
      headers: { 'Authorization': AuthService.getToken() }
    })
      .catch(err => {
        console.log(err);
        return Promise.reject("Could not delete report");
      });
  }

  getAllCommentReports() {
    return axios({
      method: 'get',
      url: api + 'reportComments',
      headers: { 'Authorization': AuthService.getToken() }
    })
      .then(response => response.data)
      .catch(err => console.log(err));
  }

  deleteCommentReport(reportId) {
    return axios({
      method: 'delete',
      url: api + 'reportComments/' + reportId,
      headers: { 'Authorization': AuthService.getToken() }
    })
  }
};

var PlaygroundAPI = new API();

export default PlaygroundAPI;
