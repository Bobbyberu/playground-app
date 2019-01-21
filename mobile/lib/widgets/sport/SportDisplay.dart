
import 'package:Playground/entities/Sport.dart';
import 'package:flutter/material.dart';

class SportDisplay extends StatelessWidget {

  final Sport sport;

  const SportDisplay({Key key, this.sport}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          new Padding(
              padding: EdgeInsets.only(right: 12),
              child: (sport.symbol != null) ?
                new Text(sport.symbol, style: new TextStyle(fontSize: 20)) :
                new Text("")
          ),
          new Text(sport.name),
        ]
    );
  }



}