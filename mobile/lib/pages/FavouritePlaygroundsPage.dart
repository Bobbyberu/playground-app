
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/services/UserService.dart';
import 'package:Playground/widgets/playground/PlaygroundCard.dart';
import 'package:flutter/material.dart';

class FavouritePlaygroundsPage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new FavouritePlaygroundsPageState();

}

class FavouritePlaygroundsPageState extends State<FavouritePlaygroundsPage> {

  Set<Playground> favouritePlaygrounds;
  UserService _userService = new UserService();

  bool _isLoading;

  @override
  void initState() {
    favouritePlaygrounds = new Set();
    _isLoading = true;
    loadFavourites();
    super.initState();
  }

  void loadFavourites() async {
    await _userService.getFavouritesPlaygrounds().then((response){
      setState(() {
        favouritePlaygrounds = response;
        _isLoading = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> playgroundCards = new List();
    favouritePlaygrounds.forEach((pg) {
      playgroundCards.add(new Padding(padding: EdgeInsets.only(bottom: 8), child: new PlaygroundCard(playground: pg, showImage: true)));
    });
    playgroundCards.add(new Container(height: 200));

    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Mes Playgrounds favoris"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(
            minHeight: MediaQuery.of(context).size.height,
            minWidth: MediaQuery.of(context).size.width,
            maxHeight: MediaQuery.of(context).size.height,
            maxWidth: MediaQuery.of(context).size.width
          ),
          child: new Padding(
            padding: EdgeInsets.all(12),
            child:
            (_isLoading) ?
            new ListView(
                scrollDirection: Axis.vertical,
                children: [
                  new Padding(
                    padding: EdgeInsets.all(24),
                    child: new Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        new CircularProgressIndicator(
                          valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor),
                        )
                      ]
                    )
                  )
                ]
            )
            : new ListView(
              scrollDirection: Axis.vertical,
              children: (favouritePlaygrounds.isEmpty) ?
              [ new Padding(
                  padding: EdgeInsets.all(24),
                  child: new Text("Vous n'avez aucun favoris pour le moment !", textAlign: TextAlign.center,)
                )
              ]
              : playgroundCards
            )
          )
        ),
      ),
    );
  }


}