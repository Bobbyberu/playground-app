import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/services/CommentService.dart';
import 'package:Playground/widgets/map/PlaygroundShowOnMap.dart';
import 'package:Playground/widgets/playground/CommentStars.dart';
import 'package:flutter/material.dart';


/**
 * Widget to display a playground details
 */
class PlaygroundDetails extends StatelessWidget {
  
  final Playground playground;

  const PlaygroundDetails({Key key, this.playground}) : super(key: key) ;

  @override
  Widget build(BuildContext context) {

    CommentService _commentService = new CommentService();
    List<Comment> comments = _commentService.getCommentsOfPlayground(this.playground.id);

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
                        image: (this.playground.imgPath != null) ? NetworkImage(this.playground.imgPath) : AssetImage("images/default_playground.png"),
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
                            tooltip: "Back",
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
                                tooltip: "Back",
                                onPressed: () {},
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
                                this.playground.name,
                                style: new TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.bold
                                ),
                              )
                            ),

                            new Text(
                              this.playground.address,
                              style: new TextStyle(
                                color: Colors.grey[700],
                              ),
                              overflow: TextOverflow.clip,
                            ),
                            new Text(
                              this.playground.city,
                              style: new TextStyle(
                                color: Colors.grey[700],
                              ),
                              overflow: TextOverflow.clip,
                            )

                          ]
                        ),

                        new IconButton(
                            icon: new Icon(
                              Icons.my_location,
                              size: 30,
                            ),
                            color: Theme.of(context).primaryColor,
                            tooltip: "Voir sur la carte",
                            onPressed: () {
                              Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundShowOnMap(playground: this.playground)));
                            }
                        )

                      ],
                    ),

                    new Padding(
                      padding: EdgeInsets.only(top: 18, left: 4),
                      child: new Row ( //AVIS
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
                                  children: this.playground.sports.map(
                                    (s) => new Padding(
                                      padding: EdgeInsets.only(bottom: 4),
                                      child: new Text(s.name),
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
                              (this.playground.description.length > 0) ? this.playground.description : "Aucune description n'est disponible",
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