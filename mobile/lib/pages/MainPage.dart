
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:Playground/widgets/map/PlaygroundMarker.dart';
import 'package:Playground/widgets/playground/PlaygroundCard.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong/latlong.dart';


///
/// Home page widget of the application
/// Page that display
/// - Map
/// - Searchbar
/// - Links to "Add playground" page and "Profile" page
///
class MainPage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new MainPageState();

}

class MainPageState extends State<MainPage> {

  PlaygroundService _playgroundService = new PlaygroundService();
  List<Playground> results;
  bool searching;

  @override
  void initState() {
    searching = false;
    results = _playgroundService.getPlaygroundsNearMe();
    super.initState();
  }

  void search(String value) async {
    await _playgroundService.search(value).then((response) {
      setState(() {
        results = response;
        searching = (value.length > 0);
      });
    });
  }

  @override
  Widget build(BuildContext context) {

    List<Marker> markers = new List<Marker>();
    results.forEach((pg) { markers.add(new PlaygroundMarker(
        playground: pg,
        onPressed : () {
          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundDetails(playground: pg)));
        }
    )); });


    var searchBar = new Container(
      width: MediaQuery.of(context).size.width,
      decoration: new BoxDecoration(
        borderRadius: BorderRadius.only(bottomLeft: Radius.circular(20), bottomRight: Radius.circular(20)),
        color: (searching) ? Colors.white : Colors.transparent,
        boxShadow:(searching) ? [new BoxShadow(
            color: Theme.of(context).primaryColorDark,
            blurRadius: 6.0,
            spreadRadius: 1
        )] : []
      ),
      child: new SafeArea(
        child: new Padding(
          padding: EdgeInsets.only(top:12, bottom: 0, left: 12, right: 12),
          child:  new Column(
            mainAxisAlignment: MainAxisAlignment.end,
            mainAxisSize: MainAxisSize.max,
            children: <Widget>[
              new TextField(
                style: new TextStyle(
                  color: Theme.of(context).primaryColorDark,
                  fontSize: 18,
                ),
                decoration: new InputDecoration(
                  border: OutlineInputBorder(borderSide: BorderSide(style: BorderStyle.solid,width: 2,color: Theme.of(context).primaryColorLight), borderRadius: BorderRadius.circular(10)),
                  suffixIcon: (searching) ? new Icon(Icons.clear) : new Icon(Icons.search),
                  contentPadding: EdgeInsets.only(left: 12),
                  hintText: "Rechercher un terrain",
                  filled: true,
                  fillColor: Colors.white
                ),
                onSubmitted: (value) {
                  search(value);
                }
              ),

              (searching) ?
              new Padding(
                padding: EdgeInsets.only(top: 18),
                child:new Column( // Results
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    new Padding(
                      padding: EdgeInsets.only(left: 12, bottom: 8),
                      child: new Text(results.length.toString() + " résultat(s) trouvés")
                    ),
                    new SingleChildScrollView(
                      scrollDirection: Axis.vertical,
                      child: new ConstrainedBox(
                        constraints: new BoxConstraints(minHeight: 100, maxHeight: 200),
                        child: new ListView(
                          scrollDirection: Axis.vertical,
                          children: List.generate(results.length, (index) => new Padding(padding: EdgeInsets.only(bottom: 4), child: new PlaygroundCard(playground: results.elementAt(index)))),
                        ),
                      ),
                    )
                  ],
                )
              ) : new Column()
            ],
          )
        )
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
                      ),
                      new MarkerLayerOptions(
                          markers: markers
                      )
                    ]
                ),
              ),

              new Align(
                alignment: Alignment.bottomRight,
                child: new Padding(
                  padding: EdgeInsets.all(24),
                  child: new Container(
                    constraints: BoxConstraints.tightFor(width: 60,height: 60),
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(50),
                      color: Theme.of(context).primaryColor,
                      boxShadow: [new BoxShadow(
                        color: Theme.of(context).primaryColorDark,
                        blurRadius: 6.0,
                        spreadRadius: 1
                      ),]
                    ),
                    child: new IconButton(
                        icon: new Icon(Icons.add, color: Colors.white),
                        tooltip: "Nouveau playground",
                        onPressed: () {
                          Navigator.pushNamed(context, '/newplayground');
                        }
                    ),
                  )
                )
              ),

              new Align(
                alignment: Alignment.bottomLeft,
                child: new Padding(
                    padding: EdgeInsets.all(24),
                    child: new Container(
                      constraints: BoxConstraints.tightFor(width:60, height:60),
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(50),
                        color: Theme.of(context).primaryColor,
                        boxShadow: [new BoxShadow(
                            color: Theme.of(context).primaryColorDark,
                            blurRadius: 6.0,
                            spreadRadius: 1
                        ),]
                      ),
                      child: new IconButton(
                          icon: new Icon(Icons.person, color: Colors.white),
                          tooltip: "Profil",
                          onPressed: () {
                            Navigator.pushNamed(context, '/profile');
                          }
                      ),
                    )
                )
              ),

              new Positioned(
                  top: 0,
                  child: searchBar
              ),

            ],
          ),
        )


    );

  }

}