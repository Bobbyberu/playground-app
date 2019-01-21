import 'package:Playground/enums/ConnectionStatus.dart';
import 'package:Playground/pages/AddPlaygroundPage.dart';
import 'package:Playground/pages/CGUPage.dart';
import 'package:Playground/pages/FavouritePlaygroundsPage.dart';
import 'package:Playground/pages/LoginPage.dart';
import 'package:Playground/pages/MainPage.dart';
import 'package:Playground/pages/ProfilePage.dart';
import 'package:Playground/pages/SignUpPage.dart';
import 'package:Playground/pages/StartPage.dart';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/services/TokenManager.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter/animation.dart';
import 'package:flutter/services.dart';

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
        '/'               : (context) => new StartPage(),
        '/splash'         : (context) => new PlaygroundSplashScreen2(),
        '/login'          : (context) => new LoginPage(),
        '/signup'         : (context) => new SignUpPage(),
        '/home'           : (context) => new MainPage(),
        '/cgu'            : (context) => new CGUPage(),
        '/newplayground'  : (context) => new AddPlaygroundPage(),
        '/profile'        : (context) => new ProfilePage(),
        '/favourites'     : (context) => new FavouritePlaygroundsPage()
      }
    );
  }
}


class PlaygroundSplashScreen2 extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new PlaygroundSplashScreen2State();

}

class PlaygroundSplashScreen2State extends State<PlaygroundSplashScreen2> with TickerProviderStateMixin{

  CurvedAnimation curvedAnimation;
  Tween<double> tween;
  AnimationController controller;

  double widthPerc;
  double heightPerc;
  double maxWidth;

  ConnectionStatus connectionStatus;


  void checkAuth() async {
    // Load token before executing a web request
    String token = await TokenManager.getInstance().getToken();

    // Depending on the check result, load user and set the page connectionStatus
    AuthService authService = new AuthService();
    await authService.checkConnection().then((response) async {
      if (response == ConnectionStatus.AUTHENTICATED) {
        await SessionManager.getInstance().loadUser(token);
      }
      setState(() {
        connectionStatus = response;
      });
    });
  }


  @override
  void initState() {
    super.initState();

    controller = AnimationController(
        duration: const Duration(milliseconds: 2000),
        vsync: this
    );

    curvedAnimation = new CurvedAnimation(
      parent: controller,
      curve: new Interval(
        0.0,
        1.0,
        curve: Curves.bounceIn,
      ),
    );

    curvedAnimation.addListener(() {
      setState(() {
        if (curvedAnimation.value > 0.25 && curvedAnimation.value <= 0.85) widthPerc = curvedAnimation.value * 100/0.85;
      });
    });
    controller.forward();

    heightPerc = 34;
    widthPerc = 25;

    checkAuth();
  }


  @override
  Widget build(BuildContext context) {
    // Force Portrait mode
    SystemChrome.setPreferredOrientations([
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown,
    ]);

    if(connectionStatus != null && curvedAnimation.value == 1) {
      if (connectionStatus == ConnectionStatus.AUTHENTICATED)
        return new MainPage();
      else if (connectionStatus == ConnectionStatus.NOT_AUTHENTICATED)
        return new LoginPage();
      else
        return new Material(
          color: Theme.of(context).primaryColor,
          child: PlaygroundDialog.getNoConnectionAlertDialog(context, () {Navigator.of(context).pushReplacementNamed("/splash");})
        );
    }

    // Image dimension
    maxWidth = MediaQuery.of(context).size.width * 0.85;
    var height = maxWidth * heightPerc / 100;
    var width = maxWidth * widthPerc / 100;

    return new Material(
      color: Theme.of(context).primaryColor,
      child: Column(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Image.asset(
            "images/logo_full.png",
            height: height,
            width: width,
            fit: BoxFit.fitHeight,
            alignment: Alignment.centerLeft,
          ),
          new Padding(
            padding: EdgeInsets.all(24),
            child: CircularProgressIndicator(
              valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColorLight)
            ),
          )
        ],
      ),
    );
  }

  dispose() {
    controller.dispose();
    super.dispose();
  }
}