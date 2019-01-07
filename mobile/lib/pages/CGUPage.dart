import 'package:flutter/material.dart';

class CGUPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: new AppBar(
        title: new Text("Conditions générales d'utilisation"),
      ),
      body: new Material(
        child: new Container(
          constraints: BoxConstraints.expand(),
          child: new Column(
            mainAxisSize: MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              new Text("Il faut être gentil"),
              new Text("svp")
            ],
          )
        )
      )

    );
  }

}