import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:flutter/material.dart';

class PlaygroundCard extends StatelessWidget {

  final Playground playground;
  final bool showImage;

  PlaygroundCard({this.playground, this.showImage = false});

  @override
  Widget build(BuildContext context) {
    return new Card(
      child:
      new InkWell(
        highlightColor: Theme.of(context).primaryColorLight,
        splashColor: Theme.of(context).primaryColorLight,
        child: new Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            (showImage) ? new Container(
              height: 100,
              width: MediaQuery.of(context).size.width,
              child: FadeInImage(
                image: (playground.imgPath != null) ? NetworkImage(playground.imgPath) : AssetImage("images/default_playground.png"),
                fit: BoxFit.cover,
                placeholder: AssetImage("images/playground_placeholder.png"),
                height: 100.0,
              ),
            ) : new Container(),

            new Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[

                new Padding(
                    padding: EdgeInsets.only(left: 12),
                    child: new Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        new Text((playground.name != null) ? playground.name : "Sans nom",
                          style: new TextStyle(
                              fontWeight: FontWeight.bold
                          ),
                        ),
                        new Text(playground.address + " " + playground.city,
                          style: new TextStyle(
                              color: Colors.grey[500]
                          ),
                        )
                      ],
                    )
                ),

                new IconButton(
                  icon: new Icon(
                    Icons.arrow_forward_ios,
                    color: Colors.grey[300],
                  ),
                  onPressed: () {},
                )

              ],
            ),

          ],
        ),

        onTap: () {
          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundDetails(playground: playground)));
        }
      )

    );
  }

}