import 'package:Playground/entities/Playground.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:flutter/material.dart';

class PlaygroundCard extends StatelessWidget {

  final Playground playground;

  PlaygroundCard(this.playground);

  @override
  Widget build(BuildContext context) {
    return new Card(
      child:
      new InkWell(
        highlightColor: Theme.of(context).primaryColorLight,
        splashColor: Theme.of(context).primaryColorLight,
        child: new Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            new Padding(
              padding: EdgeInsets.only(left: 12),
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  new Text(playground.name,
                    style: new TextStyle(
                        fontWeight: FontWeight.bold
                    ),
                  ),
                  new Text(playground.adresse,
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
                )
            )

          ],
        ),
        onTap: () {
          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new PlaygroundDetails(playground: playground)));
        }
      )

    );
  }

}