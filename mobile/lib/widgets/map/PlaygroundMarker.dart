import 'package:Playground/entities/Playground.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong/latlong.dart';

class PlaygroundMarker extends Marker {

  final LatLng latLng;
  final VoidCallback onPressed;
  final Playground playground;

  PlaygroundMarker({this.latLng, this.playground, this.onPressed}) : super (
      point: (playground != null) ? new LatLng(playground.latitude, playground.longitude) : latLng,
      width: 60.0,
      height: 60.0,
      builder: (context) => new Container(
        child: new InkWell(
          child: Image.asset("images/playgroundMarker.png", width: 60, height: 60),
          onTap: onPressed,
        ),
      )
  );



}