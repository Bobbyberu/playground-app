import React, { Component } from 'react';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';
import PlaygroundDetails from '../playground-details/playground-details';
import './home.css';
import NavBar from "../../common-components/nav-bar/nav-bar";
import PlaygroundAPI from '../../services/playground-api';

var Tools = require('../../services/tools');

export default class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            location: undefined,
            zoom: 15,
            name: '',
            adress: '',
            playgrounds: undefined
        }
        this.api = new PlaygroundAPI();
    }

    componentDidMount() {
        let currentLocation, currentName, currentAdress;
        Tools.getLocation(function (position) {
            currentLocation = [position.coords.latitude, position.coords.longitude];
            this.setState({
                location: currentLocation
            });
        }.bind(this));

        // Récupérer les propriétés lieés au terrain du marqueur, pour l'instant de la position courante
        this.setState({
            name: 'Abdel la cisah akay limpulsif',
            adress: '53 rue des pruniers 69007 Lyon',
        })
        this.api.getSearchResult('test')
            .then((response) => {
                this.setState({
                    playgrounds: response
                });
            });
    }


    render() {
        console.log(this.state.playgrounds);
        return (
            <div>
                <NavBar />
                <Map center={this.state.location} zoom={this.state.zoom} className="fullscreen">
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                    />
                    <Marker position={this.state.location}>
                        <Popup>
                            {/* Afficher le composant détails du playground avec les props associées au marqueur*/}
                            <PlaygroundDetails
                                name={this.state.name}
                                adress={this.state.adress}
                            />
                        </Popup>
                    </Marker>
                </Map>
            </div>
        );
    }
}