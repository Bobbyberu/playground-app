
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
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

  final GlobalKey<ScaffoldState> _scaffoldKey = new GlobalKey<ScaffoldState>();
  final GlobalKey<FormState> _searchKey = new GlobalKey<FormState>();

  PlaygroundService _playgroundService = new PlaygroundService();
  List<Playground> results;
  bool searching;

  @override
  void initState() {
    searching = false;
    results = new List<Playground>();
    loadPlaygroundsNearMe();
    super.initState();
  }

  void loadPlaygroundsNearMe() async {
    _playgroundService.getPlaygroundsNearMe().then((response){
      setState(() {
        results = response;
      });
    });
  }

  void search(String value) async {
    await _playgroundService.search(value).then((response) {
      setState(() {
        results = response;
        searching = (value.length > 0);

        if(searching) {
          _scaffoldKey.currentState.showBottomSheet<Null>((BuildContext context) {
            return new Container(
              height:  MediaQuery.of(context).size.height / 2,
              width: MediaQuery.of(context).size.width,
              decoration: new BoxDecoration(
                color: Colors.white,
                boxShadow: [
                  new BoxShadow(
                      color: Colors.grey[800],
                      blurRadius: 4.0,
                      spreadRadius: 1
                  )
                ]
              ),
              child :
                  new Padding(
                    padding: EdgeInsets.only(top: 12),
                    child: new SafeArea(
                        bottom: true,
                        child: new SingleChildScrollView(
                          child: new ConstrainedBox(
                              constraints: new BoxConstraints(),
                              child: new Padding(
                                  padding: EdgeInsets.all(12),
                                  child : new Column(
                                    children: <Widget>[
                                      new Padding(
                                        padding: EdgeInsets.only(top: 8, bottom: 8),
                                        child: new Text( results.length.toString() + " résultat(s) trouvé(s)"),
                                      ),
                                      new Column(
                                        mainAxisSize: MainAxisSize.min,
                                        children: List.generate(results.length, (index) => new Padding(padding: EdgeInsets.only(bottom: 4), child: new PlaygroundCard(playground: results.elementAt(index)))),
                                      )
                                    ],
                                  )
                              )
                          ),
                        )
                    )
                  )
            );
          })
          .closed
          .whenComplete(() {
            setState(() {
              searching = false;
            });
          });
        }

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


    TextEditingController _searchbarController = new TextEditingController();
    var searchBar = new Container(
      width: MediaQuery.of(context).size.width,
      decoration: new BoxDecoration(
        borderRadius: BorderRadius.only(bottomLeft: Radius.circular(20), bottomRight: Radius.circular(20)),
        color:  Colors.transparent,
      ),
      child: new SafeArea(
        child: new Padding(
          padding: EdgeInsets.only(top:12, bottom: 0, left: 12, right: 12),
          child: new Form(
              key: _searchKey,
              child:  new Stack(
                  alignment: const Alignment(0.99, 0.8),
                  children: <Widget>[
                    new TextFormField(
                        controller: _searchbarController,
                        style: new TextStyle(
                          color: Theme.of(context).primaryColorDark,
                          fontSize: 18,
                        ),
                        decoration: new InputDecoration(
                          border: OutlineInputBorder(borderSide: BorderSide(style: BorderStyle.solid,width: 2,color: Theme.of(context).primaryColorLight), borderRadius: BorderRadius.circular(10)),
                          contentPadding: EdgeInsets.only(left: 12, right: 36, top: 12, bottom: 12),
                          hintText: "Rechercher un terrain",
                          filled: true,
                          fillColor: Colors.white,
                        ),
                        onFieldSubmitted: (value) {
                          search(value);
                        }
                    ),
                    new IconButton(
                        onPressed: () {
                          if(!searching){
                            search(_searchbarController.text);
                          } else {
                            _searchbarController.clear();
                            setState(() {
                              searching = false;
                            });
                          }
                        },
                        icon: (searching) ? new Icon(Icons.clear, color: Colors.grey) : new Icon(Icons.search, color: Theme.of(context).primaryColor)
                    )
                  ]
              )
          )
        ),
      ),
    );



    return new Scaffold(
        key: _scaffoldKey,
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