import 'package:Playground/services/AuthService.dart';
import 'package:Playground/validators/EmailValidator.dart';
import 'package:Playground/widgets/style/PlaygroundLoginTextFieldStyle.dart';
import 'package:flutter/material.dart';

/**
 * Widget page for login.
 * First page the user should see if not connected
 * Simple form email/password to authenticate in the application
 */
class LoginPage extends StatefulWidget {

  //TODO check if user already connected ==> redirect to /home

  @override
  State<StatefulWidget> createState() => new LoginPageState();

}

class LoginPageState extends State<LoginPage> {

  String _email;
  String _password;
  String _errorMessage;
  AuthService _authService;
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();

  @override
  void initState() {
    _email = "";
    _password = "";
    _errorMessage = "";
    _authService = new AuthService();
    super.initState();
  }

  void validateForm() async {
    if (_formKey.currentState.validate()) {
      bool result = await _authService.login(_email, _password);
      if (result) {
        Navigator.pushReplacementNamed(context, '/home');
      } else {
        setState(() {
          _errorMessage = "Identifiants incorrects";
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Theme.of(context).primaryColor,
      child: new Form(
        key: _formKey,
        child: new Column(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            new Padding(
              padding: EdgeInsets.only(top:20),
              child: Image.asset(
                "images/logo_alpha.png",
                height: 140,
              )
            ),

            new Padding(
              padding: EdgeInsets.only(top:18, bottom: 18, left:28, right: 28),
              child: new TextFormField(
                initialValue: _email,
                keyboardType: TextInputType.emailAddress,
                style: PlaygroundLoginTextFieldStyle.getStyle(context),
                decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Email", Icons.mail_outline),
                validator: (value) {
                  if (value.isEmpty) return "Le champ Email est obligatoire";
                  if (!EmailValidator.isEmail(value)) return "L'email n'est pas valide";
                },
                onSaved: (value) {
                  _email = value;
                },
              ),
            ),

            new Padding(
              padding: EdgeInsets.only(top:18, bottom: 18, left:28, right: 28),
              child: new TextFormField(
                initialValue: _password,
                style: PlaygroundLoginTextFieldStyle.getStyle(context),
                decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Mot de passe", Icons.vpn_key),
                obscureText: true,
                validator: (value) {
                  if (value.isEmpty) return "Le champ Mot de passe est obligatoire";
                },
                onSaved: (value) {
                  _password = value;
                },
              ),
            ),

           (_errorMessage.isNotEmpty) ? new Padding(
              padding: EdgeInsets.only(top:8, bottom: 8, left:28, right: 28),
              child: new Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  new Padding(
                    padding: EdgeInsets.only(right: 8),
                    child: new Icon(Icons.error_outline, color: Theme.of(context).primaryColorDark),
                  ),
                  new Text(
                    _errorMessage,
                    style: new TextStyle(
                        color: Theme.of(context).primaryColorDark
                    ),
                  ),
                ]
              )
            ) : new Material(),

            new Padding(
                padding: EdgeInsets.only(top:18, bottom: 18, left:28, right: 28),
                child: new MaterialButton(
                    child: new Text(
                        "Se connecter",
                        style: new TextStyle(
                          color: Colors.white,
                          fontSize: 24,
                        )

                    ),
                    color: Theme.of(context).primaryColorDark,
                    padding: EdgeInsets.only(left: 22, right: 22, top: 10, bottom: 10),
                    onPressed: validateForm
                ),
            ),

            new Padding(
              padding: EdgeInsets.only(top:18, bottom: 50, left:24, right: 24),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  new Text(
                    "Pas encore inscrit ? ",
                    style: new TextStyle(
                        color: Colors.white,
                        fontSize: 16
                    ),
                  ),
                  GestureDetector(
                    child: new Text(
                      "Inscrivez-vous",
                      style: new TextStyle(
                        color: Colors.white,
                        fontSize: 16,
                        decoration: TextDecoration.underline
                      )
                    ),
                    onTap: () {
                      Navigator.pushNamed(context, '/signup');
                    },
                  )
                ],
              )

            ),


          ],
        ),
      ),

    );
  }

}