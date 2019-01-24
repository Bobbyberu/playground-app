import React, { Component } from 'react';
import L from 'leaflet';
import { Map, Marker, Popup, TileLayer } from 'react-leaflet';

import Snackbar from '@material-ui/core/Snackbar';

import ButtonAdd from "./add-playground/button-add";
import ModalPlayground from "./add-playground/modal-playground";
import NavBar from "../../common-components/nav-bar/nav-bar";
import PlaygroundAPI from '../../services/playground-api';
import PlaygroundDetails from '../playground-details/playground-details';
import SnackbarContentWrapper from '../../common-components/snackbar/SnackbarContentWrapper';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';

import NominatimAPI from '../../services/nominatimAPI';

import './home.css';

var Tools = require('../../services/tools');

// MUI theme
const theme = createMuiTheme({
    palette: {
        primary: {
            main: green[500],
            contrastText: '#fff'
        },
        secondary: {
            main: green[500],
            contrastText: '#fff'
        }
    }
});

// create Playground Marker for map 
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
            playgrounds: [],
            snackbarOpen: false
        }
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
        PlaygroundAPI.getAllPlayground()
            .then((response) => {
                this.setState({
                    playgrounds: response
                });
            })
            .catch(err => {
                console.log(err);
                this.setState({
                    snackbarOpen: true
                })
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

    // fat arrow for binding
    getSnackbarErrorMessage = () => {
        return 'Les playgrounds n\'ont pas pu être récupérés';
    }

    handleSnackbarClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        this.setState({
            snackbarOpen: false
        })
    }

    render() {
        return (
            <MuiThemeProvider theme={theme}>
                <div>
                    <NavBar searchbar={true} />
                    <Map center={this.state.location} zoom={this.state.zoom} className="fullscreen">
                        <TileLayer
                            url="https://api.mapbox.com/styles/v1/playground-app/cjqgjco2v0en02squr5fkrcb9/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoicGxheWdyb3VuZC1hcHAiLCJhIjoiY2pxZ2piYXdhMDBkOTQzcG5zcG9idWNrMCJ9.H64SLGKZlHfQeDTBGidTqQ"
                        />
                        {this.renderPlaygrounds()}
                    </Map>
                    <ButtonAdd />
                    <ModalPlayground />
                    <Snackbar
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'left',
                        }}
                        open={this.state.snackbarOpen}
                        autoHideDuration={6000}
                        onClose={this.handleSnackbarClose}
                    >
                        <SnackbarContentWrapper
                            onClose={this.handleSnackbarClose}
                            variant="error"
                            message={this.getSnackbarErrorMessage()}
                        />
                    </Snackbar>
                </div>
            </MuiThemeProvider>
        );
    }
}

export default Home