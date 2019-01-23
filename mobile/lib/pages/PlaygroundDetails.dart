import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundCommentPage.dart';
import 'package:Playground/pages/ReportPlaygroundPage.dart';
import 'package:Playground/services/CommentService.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:Playground/services/UserService.dart';
import 'package:Playground/widgets/map/PlaygroundShowOnMap.dart';
import 'package:Playground/widgets/playground/CommentStars.dart';
import 'package:Playground/widgets/playground/FavouritePlaygroundToggle.dart';
import 'package:Playground/widgets/sport/SportDisplay.dart';
import 'package:flutter/material.dart';


class PlaygroundDetails extends StatefulWidget {

  final Playground playground;

  const PlaygroundDetails({Key key, this.playground}) : super(key: key) ;

  @override
  State<StatefulWidget> createState() => new PlaygroundDetailsState();

}

///
///Widget to display a playground details
///
class PlaygroundDetailsState extends State<PlaygroundDetails> {

  CommentService _commentService = new CommentService();
  List<Comment> comments;
  bool isFavorite;

  @override
  void initState() {
    comments = new List<Comment>();
    isFavorite = false;
    loadComments();
    super.initState();
  }

  void loadComments() async {
    await _commentService.getCommentsOfPlayground(widget.playground.id).then((response) {
      setState(() {
        comments = response;
      });
    });
  }

  @override
  Widget build(BuildContext context) {

    return new Material(
      child: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),

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
                      FadeInImage(
                        image: NetworkImage(PlaygroundService.getPlaygroundImageUrl(widget.playground)),
                        fit: BoxFit.cover,
                        placeholder: AssetImage("images/playground_placeholder.png"),
                        height: 200.0,
                      ),

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
                                  Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new SignalPlaygroundPage(widget.playground))).then((_) => loadComments());
                                },
                              )
                          )
                      )
                    ],
                  )
              ),

              new Padding(
                padding: EdgeInsets.all(24),
                child: new Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[

                    new Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[

                        new Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[

                            new Padding(
                                padding: EdgeInsets.only(bottom: 4),
                                child: new Text(
                                  widget.playground.name,
                                  style: new TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold
                                  ),
                                )
                            ),

                            new InkWell(
                              child: new Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[
                                  new Text(
                                    widget.playground.address,
                                    style: new TextStyle(
                                      color: Colors.grey[700],
                                    ),
                                    overflow: TextOverflow.clip,
                                  ),
                                  new Text(
                                    widget.playground.city,
                                    style: new TextStyle(
                                      color: Colors.grey[700],
                                    ),
                                    overflow: TextOverflow.clip,
                                  )
                                ],
                              ),
                              onTap: () {
                                Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundShowOnMap(playground: widget.playground))).then((_) => loadComments());
                              }
                            ),

                            Padding(
                              padding: EdgeInsets.only(top: 8),
                              child: new Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: <Widget>[

                                  (widget.playground.isPrivate != null && widget.playground.isPrivate) ?
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

                                  (widget.playground.covered != null && widget.playground.covered) ?
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

                        new FavouritePlaygroundToggle(playground: widget.playground)

                      ],
                    ),

                    new Padding(
                      padding: EdgeInsets.only(top: 18, left: 4),
                      child: new InkWell(
                        child:
                        (comments.isEmpty) ?
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
                            new CommentsStars(mark:_commentService.getAverageOfComment(comments), markMax:5, color: Colors.grey[700]),
                            new Padding(
                                padding: EdgeInsets.only(left: 8, top: 2),
                                child: new Text(
                                  _commentService.getAverageOfComment(comments).toStringAsFixed(1) + " / 5 (" + comments.length.toString() + " avis)",
                                  style: new TextStyle(
                                    color: Colors.grey[700],
                                  ),
                                )
                            )
                          ],
                        ),
                        onTap: () {
                          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundCommentPage(playground: widget.playground))).then((_) => loadComments());
                        }
                      )
                    ),
                    
                    new Padding(
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
                                  children: widget.playground.sports.map(
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
                    ),

                    new Padding(
                      padding: EdgeInsets.only(top: 18),
                      child: new Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          new Text(
                            "Description",
                            style: new TextStyle(
                                fontSize: 18
                            ),
                          ),
                          new Padding(
                            padding: EdgeInsets.all(8),
                            child: new Text(
                              (widget.playground.description != null && widget.playground.description.length > 0) ? widget.playground.description : "Aucune description n'est disponible",
                              textAlign: TextAlign.justify,
                            ),
                          )
                        ],
                      ),
                    ),

                  ],
                )
              )


            ],
          )

        )
      )

    );
  }

}