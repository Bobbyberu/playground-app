import 'package:Playground/pages/AddPlaygroundPageDesign.dart';
import 'package:Playground/pages/CGUPage.dart';
import 'package:Playground/pages/LoginPage.dart';
import 'package:Playground/pages/MainPage.dart';
import 'package:Playground/pages/ProfilePage.dart';
import 'package:Playground/pages/SignUpPage.dart';
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
      initialRoute: '/splash',
      routes: {
        '/' : (context) => new StartPage(),
        '/splash'         : (context) => new PlaygroundSlashScreen(),
        '/login'          : (context) => new LoginPage(),
        '/signup'         : (context) => new SignUpPage(),
        '/home'           : (context) => new MainPage(),
        '/cgu'            : (context) => new CGUPage(),
        '/newplayground'  : (context) => new AddPlaygroundPageDesign(),
        '/profile'        : (context) => new ProfilePage(),
      }
    );
  }
}

class PlaygroundSlashScreen extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new PlaygroundSlashScreenState();

}

class PlaygroundSlashScreenState extends State<PlaygroundSlashScreen> {

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
