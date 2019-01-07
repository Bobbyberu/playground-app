import 'package:Playground/pages/StartPage.dart';
import 'package:flutter/material.dart';
import 'package:splashscreen/splashscreen.dart';

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
      home: new PlaygroundSlapshScreen()
    );
  }
}

class PlaygroundSlapshScreen extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new PlaygroundSlapshScreenState();

}

class PlaygroundSlapshScreenState extends State<PlaygroundSlapshScreen> {

  @override
  Widget build(BuildContext context) {
    return new SplashScreen(
      seconds: 3,
      navigateAfterSeconds: new StartPage(),
      image: Image.asset("images/logo_alpha.png"),
      photoSize: 80,
      title: new Text(""),
      styleTextUnderTheLoader: TextStyle(fontSize: 0),
      loaderColor: Theme.of(context).primaryColorLight,
      backgroundColor: Theme.of(context).primaryColor,
    );
  }

}
