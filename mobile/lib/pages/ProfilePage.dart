import 'package:Playground/entities/User.dart';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/widgets/menu/SettingsMenuLink.dart';
import 'package:Playground/widgets/user/UserAvatarCircle.dart';
import 'package:flutter/material.dart';

///
/// Widget page of the profile menu
///
class ProfilePage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new ProfilePageState();

}


class ProfilePageState extends State<ProfilePage> {

  AuthService _authService = new AuthService();
  User user = SessionManager.getInstance().getUser();

  TextStyle menuStyle =  new TextStyle(
      fontSize: 20,
      color: Colors.grey[800]
  );

  @override
  void initState() {
    super.initState();

    user = SessionManager.getInstance().getUser();
    if (user == null) {

    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Profil"),
      ),
      body: new Material(
        child: new Container(
          constraints: BoxConstraints.expand(),
          child: new Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              new Card(
                child: new Container(
                  constraints: new BoxConstraints(minWidth: MediaQuery.of(context).size.width),
                  child: new Padding(
                    padding: EdgeInsets.all(8),
                    child: new Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[

                        new UserAvatarCircle(user: user, size: 100),

                        new Text(
                          (user.username != null) ? "@ " + user.username : "",
                          style: new TextStyle(
                              color: Colors.grey[700],
                              fontStyle: FontStyle.italic,
                              fontSize: 22
                          ),
                        )

                      ],
                    )
                  )
                )
              ),

              new SettingsMenuLink(label: "Modifier mon profil", icon: Icons.person, onTap: () {
                Navigator.of(context).pushNamed("/profileUpdate");
              }),
              new Divider(),

              new SettingsMenuLink(label: "Mes Playgrounds favoris", icon: Icons.favorite, onTap: () {
                Navigator.pushNamed(context, '/favourites');
              }),
              new Divider(),

              new SettingsMenuLink(label: "CGU", icon: Icons.format_align_justify, onTap: () { Navigator.pushNamed(context, '/cgu'); }),
              new Divider(),

              new SettingsMenuLink(label: "Déconnexion", icon: Icons.exit_to_app, onTap: () async {
                _authService.logout();
                Navigator.pushReplacementNamed(context, '/');
              }),
              new Divider()

              ],
            )

        )

      )
    );
  }

}