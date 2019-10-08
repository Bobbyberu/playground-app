import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/entities/User.dart';
import 'package:Playground/pages/PlaygroundCommentPage.dart';
import 'package:Playground/pages/ReportPlaygroundPage.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/widgets/map/PlaygroundShowOnMap.dart';
import 'package:Playground/widgets/playground/CommentStars.dart';
import 'package:Playground/widgets/playground/FavouritePlaygroundToggle.dart';
import 'package:Playground/widgets/sport/SportDisplay.dart';
import 'package:Playground/widgets/sport/UniqueSportSelection.dart';
import 'package:Playground/widgets/user/UserSummary.dart';
import 'package:flutter/material.dart';


class PlaygroundDetails extends StatefulWidget {

  final Playground playground;

  const PlaygroundDetails({Key key, this.playground}) : super(key: key) ;

  @override
  State<StatefulWidget> createState() => new PlaygroundDetailsState(playground.id);

}

///
/// Widget to display a playground details
///
class PlaygroundDetailsState extends State<PlaygroundDetails> {

  final int playgroundId;
  Playground _playground;

  PlaygroundService _playgroundService = new PlaygroundService();
  List<User> players;
  bool isFavorite;

  bool _isPlayingLoading;

  PlaygroundDetailsState(this.playgroundId);

  // Page loading

  @override
  void initState() {
    isFavorite = false;
    _isPlayingLoading = false;
    loadPlayground();
    loadPlayers();
    super.initState();
  }

  void loadPlayground() async {
    await _playgroundService.getPlaygroundById(playgroundId).then((response) {
      setState(() {
        _playground = response;
      });
    });
  }

  void loadPlayers() async {
    await _playgroundService.getPlayingUser(playgroundId).then((response) {
      setState(() {
        players = response;
      });
    });
  }

  // Page building

  @override
  Widget build(BuildContext context) {

    return new Material(
      child: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(maxWidth: MediaQuery.of(context).size.width),
          child: new Column(
            children: <Widget>[

              new Container(
                  constraints: new BoxConstraints.expand(
                    height: 200
                  ),
                  color: Theme.of(context).primaryColorLight,
                  child: new Stack(
                    fit: StackFit.passthrough,
                    children: <Widget>[
                      _getPlaygroundImage(),

                      new Positioned(
                        left: 4,
                        child:
                        new SafeArea(
                          top: true,
                          child: new IconButton(
                            icon:new Icon(
                              Icons.arrow_back,
                              color: Colors.white,
                            ),
                            color: Colors.transparent,
                            tooltip: "Retour",
                            onPressed: () { Navigator.of(context).pop(); },
                          )
                        )
                      ),

                      new Positioned(
                          right: 4,
                          child:
                          new SafeArea(
                              top: true,
                              child: new IconButton(
                                icon:new Icon(
                                  Icons.flag,
                                  color: Colors.white,
                                ),
                                color: Colors.transparent,
                                tooltip: "Signaler",
                                onPressed: () {
                                  Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new SignalPlaygroundPage(_playground)));
                                },
                              )
                          )
                      )
                    ],
                  )
              ),

              (_playground != null) ?
              new Padding(
                padding: EdgeInsets.all(24),
                child: new Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[

                    _getPlaygroundMainInfo(),

                    _getPlaygroundComments(),
                    
                    _getPlaygroundSports(),

                    _getPlaygroundDescription(),

                    _getPlaygroundPlayers()

                  ],
                )
              ) :
              Column(
               crossAxisAlignment: CrossAxisAlignment.center,
               mainAxisAlignment: MainAxisAlignment.center,
               children: <Widget>[
                 Padding(
                   padding: EdgeInsets.only(top: 46),
                   child: new CircularProgressIndicator(
                      valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor)
                   )
                 )
               ],
              )

            ],
          )

        )
      )

    );
  }

  Widget _getPlaygroundImage() {
    return (_playground == null) ?
      Image(image: AssetImage("images/playground_placeholder.png"), height: 200.0, fit: BoxFit.cover) :
      FadeInImage(
        image: NetworkImage(PlaygroundService.getPlaygroundImageUrl(_playground)),
        fit: BoxFit.cover,
        placeholder: AssetImage("images/playground_placeholder.png"),
        height: 200.0,
      );
  }

  Widget _getPlaygroundMainInfo() {
    return new Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[

        new Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[

              new Padding(
                  padding: EdgeInsets.only(bottom: 4),
                  child: new Text(
                     _playground.name,
                    style: new TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                    )
                  )
              ),

              new InkWell(
                  child: new Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Container(
                        width: MediaQuery.of(context).size.width - 96,
                        child: new Text(
                          _playground.address,
                          style: new TextStyle(
                            color: Colors.grey[700],
                          ),
                          overflow: TextOverflow.ellipsis,
                          maxLines: 5,
                        )
                      )
                    ],
                  ),
                  onTap: () {
                    Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundShowOnMap(playground: _playground)));
                  }
              ),

              Padding(
                  padding: EdgeInsets.only(top: 12),
                  child: new Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[

                      (_playground.isPrivate != null && _playground.isPrivate) ?
                      new Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Padding(padding: EdgeInsets.only(right: 4), child: new Icon(Icons.lock_outline, size: 16)),
                          new Text("Privé")
                        ],
                      )
                          : new Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Padding(padding: EdgeInsets.only(right: 4), child: new Icon(Icons.lock_open, size: 16)),
                          new Text("Public")
                        ],
                      ),

                      (_playground.covered != null && _playground.covered) ?
                      new Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Padding(padding: EdgeInsets.only(right: 4), child: new Icon(Icons.home, size: 16)),
                          new Text("Couvert")
                        ],
                      )
                          : new Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Padding(padding: EdgeInsets.only(right: 4), child:new Icon(Icons.landscape, size: 16)),
                          new Text("Non couvert")
                        ],
                      ),

                    ],
                  )
              )


            ]
        ),

        new FavouritePlaygroundToggle(playgroundId: playgroundId)

      ],
    );
  }

  Widget _getPlaygroundComments() {
    return
      new Padding(
        padding: EdgeInsets.only(top: 18, left: 4),
        child: new InkWell(
            child:
            (_playground.averageMark == null) ?
            new Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Icon(Icons.star_border, color: Colors.grey[700]),
                new Padding(
                    padding: EdgeInsets.only(left: 8, top: 2),
                    child: new Text(
                      "Ce playground n'a reçu aucun avis",
                      style: new TextStyle(
                        color: Colors.grey[700],
                      ),
                    )
                )
              ],
            )
                :
            new Row ( //AVIS
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new CommentsStars(mark:_playground.averageMark, markMax:5, color: Colors.grey[700]),
                new Padding(
                    padding: EdgeInsets.only(left: 8, top: 2),
                    child: new Text(
                      _playground.averageMark.toString() + " / 5 - Voir les avis",
                      style: new TextStyle(
                        color: Colors.grey[700],
                      ),
                    )
                )
              ],
            ),
            onTap: () {
              Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundCommentPage(playground: _playground))).then((_) => loadPlayground());
            }
        )
      );
  }

  Widget _getPlaygroundSports() {
    return Padding(
      padding: EdgeInsets.only(top: 24),
      child: new Row(
        children: <Widget>[
          new Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new Text(
                "Sports",
                style: new TextStyle(
                    fontSize: 18
                ),
              ),
              new Padding(
                padding: EdgeInsets.only(left: 8,top: 8),
                child: new Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: _playground.sports.map(
                            (s) => new Padding(
                            padding: EdgeInsets.only(bottom: 4),
                            child: new SportDisplay(sport: s)
                        )
                    ).toList()
                ),
              )
            ]
          )
        ],
      ),
    );
  }

  Widget _getPlaygroundDescription() {
    return new Padding(
      padding: EdgeInsets.only(top: 18),
      child: new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new Text(
            "Description",
            style: new TextStyle(fontSize: 18),
          ),
          new Padding(
            padding: EdgeInsets.all(8),
            child: new Text(
              (_playground.description != null && _playground.description.length > 0) ? _playground.description : "Aucune description n'est disponible",
              textAlign: TextAlign.justify,
            ),
          )
        ],
      ),
    );
  }

  Widget _getPlaygroundPlayers() {
    return new Padding(
      padding: EdgeInsets.only(top: 18),
      child: new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new Text(
            "Joueurs",
            style: new TextStyle(
                fontSize: 18
            ),
          ),
          (players != null) ?
          new Padding(
              padding: EdgeInsets.only(top: 8, bottom: 8),
              child: (_isPlayingLoading) ?
              new CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor),
              )
                  :
              (players.where((u) => u.id == SessionManager.getInstance().getUser().id).isNotEmpty) ?
              new InkWell(
                child: Row(
                    children: <Widget>[
                      Icon(Icons.close, color: Colors.redAccent),
                      Text("Je ne joue plus", style: TextStyle(color: Colors.redAccent))
                    ]
                ),
                onTap: () async {
                  setState(() { _isPlayingLoading = true; });
                  await _playgroundService.addPlayerOutPlayground(_playground, SessionManager.getInstance().getUser()).then((response){
                    loadPlayers();
                    setState(() { _isPlayingLoading = false; });
                  });
                },
              )
                  : new InkWell(
                child: Row(
                    children: <Widget>[
                      Icon(Icons.add, color: Theme.of(context).primaryColor),
                      Text("Je suis en train de jouer", style: TextStyle(color: Theme.of(context).primaryColor))
                    ]
                ),
                onTap: () async {
                  Sport playedSport = await Navigator.of(context).push(
                      new MaterialPageRoute<Sport>(
                          builder: (BuildContext context) {
                            return new UniqueSportSelection(sports: _playground.sports);
                          },
                          fullscreenDialog: true
                      )
                  );
                  setState(() { _isPlayingLoading = true; });
                  await _playgroundService.addPlayerInPlayground(_playground, SessionManager.getInstance().getUser(), playedSport).then((response){
                    loadPlayers();
                    setState(() { _isPlayingLoading = false; });
                  });
                },
              )
          ) : Padding(padding: EdgeInsets.only(top: 8, bottom: 8)),

          new Padding(
            padding: EdgeInsets.all(8),
            child:
            (players == null) ?
            new Container():
            (players.isEmpty) ?
            new Text("Aucun joueur n'est actuellement sur le playground") :
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: players.map((p) => Padding(
                padding: EdgeInsets.all(8),
                child: UserSummary(user: p, avatarSize: 50, playing: true),
              )).toList(),
            ),
          )
        ],
      ),
    );
  }

}