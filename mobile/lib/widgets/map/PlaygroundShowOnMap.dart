import 'package:Playground/entities/Playground.dart';
import 'package:Playground/widgets/map/PlaygroundMarker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong/latlong.dart';

class PlaygroundShowOnMap extends StatelessWidget {

  final Playground playground;

  const PlaygroundShowOnMap({Key key, this.playground}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return
      new Scaffold(
        appBar: new AppBar(
        ),
        body: new FlutterMap(
          options: new MapOptions(
            center: new LatLng(this.playground.latitude, this.playground.longitude),
            zoom: 17.0,
          ),
          layers: [
            new TileLayerOptions(
                backgroundColor: Theme.of(context).primaryColorLight,
                urlTemplate: "https://api.mapbox.com/styles/v1/playground-app/cjqgjco2v0en02squr5fkrcb9/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoicGxheWdyb3VuZC1hcHAiLCJhIjoiY2pxZ2piYXdhMDBkOTQzcG5zcG9idWNrMCJ9.H64SLGKZlHfQeDTBGidTqQ",
            ),
            new MarkerLayerOptions(
                markers: [
                  new PlaygroundMarker(playground: this.playground, onPressed: () {})
                ]
            )
          ]
        )
    );
  }

}