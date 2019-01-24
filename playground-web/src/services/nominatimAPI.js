const axios = require('axios');
const api = 'https://nominatim.openstreetmap.org/search?q=';
const format = '&format=json';

class API {

    getPosition(address) {
        address.replace(' ', '+');
        return axios({
            method: 'get',
            url: api + address + format,
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.data)
            .catch(err => console.log(err));
    }
}

var NominatimAPI = new API();

export default NominatimAPI;