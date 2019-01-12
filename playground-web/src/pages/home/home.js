import React, { Component } from 'react';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';
import PlaygroundDetails from '../playground-details/playground-details';
import './home.css';
import NavBar from "../../common-components/nav-bar/nav-bar";
import ButtonAdd from "./add-playground/button-add";
import ModalPlayground from "./add-playground/modal-playground"
import PlaygroundAPI from '../../services/playground-api';
import L from 'leaflet';

var Tools = require('../../services/tools');

const playgroundMarkerIcon = new L.icon({
    iconUrl: require('../../assets/img/playgroundMarker.png'),
    iconRetinaUrl: require('../../assets/img/playgroundMarker.png'),
    iconAnchor: [23, 50],
    popupAnchor: [10, -44],
    iconSize: [70, 70],
    shadowSize: [68, 95],
    shadowAnchor: [20, 92],
});

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            location: undefined,
            zoom: 15,
            playgrounds: []
        }
        this.api = new PlaygroundAPI();
        this.renderPlaygrounds = this.renderPlaygrounds.bind(this);
    }

    componentDidMount() {
        let currentLocation;
        Tools.getLocation(function (position) {
            currentLocation = [position.coords.latitude, position.coords.longitude];
            this.setState({
                location: currentLocation
            });
        }.bind(this));

        // Récupérer les propriétés lieés aux terrains
        this.api.getSearchResult('test')
            .then((response) => {
                this.setState({
                    playgrounds: response
                });
            });
    }

    renderPlaygrounds() {
        let playgrounds = this.state.playgrounds;

        return (
            <div>
                {playgrounds.map(playground => (
                    <Marker key={playground.id} position={[playground.latitude, playground.longitude]} icon={playgroundMarkerIcon}>
                        <Popup>
                            <PlaygroundDetails
                                playground={playground}
                            />
                        </Popup>
                    </Marker>
                ))}
            </div>
        );
    }

    render() {
        return (
            <div>
                <NavBar />
                <Map center={this.state.location} zoom={this.state.zoom} className="fullscreen">
                    <TileLayer
                        url="https://api.mapbox.com/styles/v1/playground-app/cjqgjco2v0en02squr5fkrcb9/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoicGxheWdyb3VuZC1hcHAiLCJhIjoiY2pxZ2piYXdhMDBkOTQzcG5zcG9idWNrMCJ9.H64SLGKZlHfQeDTBGidTqQ"
                    />
                    {this.renderPlaygrounds()}
                </Map>
                <ButtonAdd />
                <ModalPlayground />
            </div>
        );
    }
}

export default Home