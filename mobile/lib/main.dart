import 'package:Playground/pages/StartPage.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      title: 'Playground',
      theme: ThemeData(
        primarySwatch:      Colors.green,
        primaryColor:       Colors.green[500],
        primaryColorLight:  Colors.grey[300],
        primaryColorDark:   Colors.green[900]
      ),
      home: new StartPage(),
    );
  }
}
