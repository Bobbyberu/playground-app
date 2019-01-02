var axios = require('axios');

export default class PlaygroundAPI {

    getSearchResult(searchTerm) {
        return axios.get('/playgrounds.json')
            .then(function (response) {
                return response.data.result;
            });
    }
}