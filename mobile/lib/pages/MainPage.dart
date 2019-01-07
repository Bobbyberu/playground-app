
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:Playground/widgets/map/PlaygroundMarker.dart';
import 'package:Playground/pages/AddPlaygroundPageDesign.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong/latlong.dart';

class MainPage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new MainPageState();

}

class MainPageState extends State<MainPage> {

  PlaygroundService _playgroundService = new PlaygroundService();

  @override
  Widget build(BuildContext context) {

    List<Marker> markers = new List<Marker>();
    _playgroundService.getPlaygroundsNearMe().forEach((pg) { markers.add(new PlaygroundMarker(
        playground: pg,
        onPressed : () {
          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundDetails(playground: pg)));
        }
    )); });


    var searchBar = new Container(
      height: 54,
      width: MediaQuery.of(context).size.width,
      child: new FractionallySizedBox(
        widthFactor: 0.9,
        child: new Container(
            decoration: new BoxDecoration(
                color: Colors.white,
                border: Border.all(color: Colors.green, width: 1, style: BorderStyle.solid),
                borderRadius: BorderRadius.circular(20),
                boxShadow: [ new BoxShadow(
                    color: Theme.of(context).primaryColorDark,
                    blurRadius: 2,
                    offset: Offset(0, 1)
                )]
            ),
            child: new FractionallySizedBox(
                widthFactor: 0.95,
                child: new TextField(
                  style: new TextStyle(
                    color: Theme.of(context).primaryColorDark,
                    fontSize: 24,
                  ),
                  decoration: new InputDecoration(
                    border: InputBorder.none,
                    suffixIcon: new Icon(Icons.search),
                  ),
                )
            )
        ),
      ),
    );

    return new Scaffold(
        body: SizedBox.expand(
          child: new Stack(
            fit: StackFit.loose,
            children: <Widget>[

              new Positioned(
                top: 0,
                left: 0,
                height: MediaQuery.of(context).size.height,
                width: MediaQuery.of(context).size.width,
                child: new FlutterMap(
                    options: new MapOptions(
                      center: new LatLng(45.764045, 4.835675),
                      zoom: 13.0,
                    ),
                    layers: [
                      new TileLayerOptions(
                          backgroundColor: Theme.of(context).primaryColorLight,
                          urlTemplate: "https://api.mapbox.com/styles/v1/playground-app/cjqgjco2v0en02squr5fkrcb9/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoicGxheWdyb3VuZC1hcHAiLCJhIjoiY2pxZ2piYXdhMDBkOTQzcG5zcG9idWNrMCJ9.H64SLGKZlHfQeDTBGidTqQ",
                          //subdomains: ['a','b','c']
                      ),
                      new MarkerLayerOptions(
                          markers: markers
                      )
                    ]
                ),
              ),



              new Positioned(
                  top: 40,
                  child: searchBar
              ),

              new Positioned(
                key: new Key("add_button"),
                right: 20,
                bottom: 20,
                child: new FloatingActionButton(
                    child: new Icon(Icons.add),
                    backgroundColor: Theme.of(context).primaryColor,
                    onPressed: () {
                      Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new AddPlaygroundPageDesign()));
                    }
                ),
              )
            ],
          ),
        )


    );

  }

}