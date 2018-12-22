import React, { Component } from 'react';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';
import './home.css';
var Tools = require('../../services/tools');

export default class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            location: undefined,
            zoom: 15
        }
    }

    componentDidMount() {
        let currentLocation;        
        Tools.getLocation(function(position) {
            currentLocation = [position.coords.latitude, position.coords.longitude];
            this.setState({
                location: currentLocation
            });
        }.bind(this));
    }

    render() {
        return (
            <Map center={this.state.location} zoom={this.state.zoom} className="fullscreen">
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                />
                <Marker position={this.state.location}>
                    <Popup>A pretty CSS3 popup.<br />Easily customizable.</Popup>
                </Marker>
            </Map>
        );
    }
}