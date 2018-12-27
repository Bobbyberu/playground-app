//import 'package:Playground/pages/AddPlaygroundPage.dart';
import 'package:Playground/pages/AddPlaygroundPageDesign.dart';
import 'package:flutter/material.dart';

class MainPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: new Material(
        color: Theme.of(context).primaryColorLight,
        child: new Container(
          constraints: BoxConstraints.expand(),
          child:
            new Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                new Title(color: Theme.of(context).primaryColorDark, child: new Text("TODO MAP"))
              ]
            )
        ),
      ),
      floatingActionButton: new FloatingActionButton(
        child: new Icon(Icons.add),
        backgroundColor: Theme.of(context).primaryColor,
        onPressed: () {
          Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new AddPlaygroundPageDesign()));
        }
      ),
    );
  }

}