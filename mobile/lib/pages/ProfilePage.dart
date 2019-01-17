import 'package:Playground/entities/User.dart';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/widgets/menu/SettingsMenuLink.dart';
import 'package:flutter/material.dart';

///
/// Widget page of the profile menu
///
class ProfilePage extends StatelessWidget {

  AuthService _authService = new AuthService();
  User user = SessionManager.getInstance().getUser();

  TextStyle menuStyle =  new TextStyle(
      fontSize: 20,
      color: Colors.grey[800]
  );

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

                        new Container(
                          decoration: new BoxDecoration(
                              shape: BoxShape.circle,
                              border: Border.all(width: 4, color: Theme.of(context).primaryColor),
                              image: DecorationImage(
                                fit: BoxFit.cover,
                                image: new NetworkImage(
                                  "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fyt3.ggpht.com%2F-Zbx5nbYMCM8%2FAAAAAAAAAAI%2FAAAAAAAAAAA%2FYZ4Lebmnjz0%2Fs900-c-k-no-rj-c0xffffff%2Fphoto.jpg&f=1",
                                ),
                              )
                          ),
                          width: 100,
                          height: 100,
                        ),

                        new Text(
                          "@ " + user.username,
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

              new SettingsMenuLink(label: "Modifier mon profil", icon: Icons.person, onTap: () { /* TODO */ }),
              new Divider(),

              new SettingsMenuLink(label: "Paramètres", icon: Icons.settings, onTap: () { /* TODO */ }),
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