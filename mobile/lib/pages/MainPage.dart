//import 'package:Playground/pages/AddPlaygroundPage.dart';
import 'package:Playground/pages/AddPlaygroundPageDesign.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong/latlong.dart';

class MainPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

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
                          urlTemplate: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                          subdomains: ['a', 'b', 'c']),
                    ]
                ),
            ),



            new Positioned(
                top: 60,
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