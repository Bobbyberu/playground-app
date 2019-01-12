const axios = require('axios');

export default class PlaygroundAPI {
  getSearchResult(searchTerm) {
    return axios.get('/playgrounds.json')
      .then(response => response.data.result);
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
}
