import 'package:Playground/controllers/SportController.dart';
import 'package:Playground/pages/AddPlaygroundPageDesign.dart';
import 'package:Playground/pages/CGUPage.dart';
import 'package:Playground/pages/LoginPage.dart';
import 'package:Playground/pages/MainPage.dart';
import 'package:Playground/pages/ProfilePage.dart';
import 'package:Playground/pages/SignUpPage.dart';
import 'package:Playground/pages/StartPage.dart';
import 'package:Playground/services/SportService.dart';
import 'package:Playground/services/TokenManager.dart';
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
        '/splash'         : (context) => new PlaygroundSplashScreen(),
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

class PlaygroundSplashScreen extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new PlaygroundSplashScreenState();

}

class PlaygroundSplashScreenState extends State<PlaygroundSplashScreen> {

  bool connected;

  void checkConnection() async {
    // TODO replace by a true check ws
    await TokenManager.getInstance().getToken();
    SportController controller = new SportController();
    await controller.getAllSports().then((response) {
      setState(() {
        connected = (response.statusCode != null && response.statusCode == 200);
      });
    }).catchError((error){
      setState(() {
        connected = false;
      });
    });
  }

  @override
  void initState() {
    connected = false;
    checkConnection();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new SplashScreen(
      seconds: 3,
      navigateAfterSeconds: new StartPage(connected: connected),
      image: Image.asset("images/logo_alpha.png"),
      photoSize: 80,
      title: new Text(""),
      styleTextUnderTheLoader: TextStyle(fontSize: 0),
      loaderColor: Theme.of(context).primaryColorLight,
      backgroundColor: Theme.of(context).primaryColor,
    );
  }

}
