var axios = require('axios');

export default class PlaygroundAPI {

    getSearchResult(searchTerm) {
        return axios.get('/playgrounds.json')
            .then(function (response) {
                return response.data.result;
            });
    }

    getRandomPlaygrounds() {
        return axios.get('/playgrounds.json')
            .then(function (response) {
                let max = response.data.result.length;

                let start = Math.floor(Math.random() * (max / 2));
                let end = Math.floor((Math.random() * max) + max / 2);
                return response.data.result.slice(start, end);
            });
    }

    getAllSports() {
        return axios.get('/sports.json')
            .then(function (response) {
                return response.data.result;
            });
    }
}