import 'package:Playground/services/AuthService.dart';
import 'package:Playground/widgets/inputs/PlaygroundCheckbox.dart';
import 'package:Playground/widgets/style/PlaygroundLoginTextFieldStyle.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class SignUpPage extends StatefulWidget {

  //TODO check if user already connected ==> redirect to home

  @override
  State<StatefulWidget> createState() => SignUpPageState();

}

class SignUpPageState extends State<SignUpPage> {

  String _email;
  String _pseudo;
  String _mdp;
  String _mdp_confirmation;
  bool _cgu_accepted;
  String _cgu_accept_message;

  AuthService _authService;
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();

  @override
  void initState() {
    _email = "";
    _pseudo = "";
    _mdp = "";
    _mdp_confirmation = "";
    _cgu_accepted = false;
    _cgu_accept_message = "";

    _authService = new AuthService();
    super.initState();
  }

  void validateForm() async {
    (!_cgu_accepted) ? setState(() { _cgu_accept_message = "Vous devez accepter les CGU"; }) : setState(() { _cgu_accept_message = ""; }) ;
    if (_formKey.currentState.validate()) {
      if (_cgu_accepted) {
        bool result = await _authService.signup();
        Widget dialogTitle;
        var dialogContent;
        VoidCallback dialogOnPressed;

        if (result) {
          dialogTitle = new Text("Yeah !");
          dialogContent = new Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              new Text("Votre inscription est terminée !"),
              new Text("Un email de confirmation va vous être envoyé")
            ]
          );
          dialogOnPressed = () {
            Navigator.pushReplacementNamed(context, "/home");
          };
        } else {
          dialogTitle = new Text("Oh Oh...");
          dialogContent = new Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children:[
              new Text("Un problème est survenu"),
              new Text("Veuillez réessayer plus tard")
            ]
          );
          dialogOnPressed = () {
            Navigator.of(context, rootNavigator: true).pop();
          };
        }

        showDialog(
            context: context,
            builder: (BuildContext context) {
              return new AlertDialog(
                title: dialogTitle,
                content: dialogContent,
                actions: <Widget>[
                  new FlatButton(
                      onPressed: dialogOnPressed,
                      child: new Text(
                        "Ok",
                        style: new TextStyle(
                            color: Theme.of(context).primaryColor
                        ),
                      )
                  )
                ],
              );
            }
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      backgroundColor: Theme.of(context).primaryColor,
      appBar: new AppBar(
        title: new Text("Inscription"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),
          child:  new Material(
            color: Theme.of(context).primaryColor,
            child: new Form(
          key: _formKey,
          child: new Column(
            mainAxisSize: MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              new Padding(
                padding: EdgeInsets.only(top:14, bottom: 14, left:28, right: 28),
                child: new TextFormField(
                  initialValue: _email,
                  keyboardType: TextInputType.emailAddress,
                  style: PlaygroundLoginTextFieldStyle.getStyle(context),
                  decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Email", Icons.mail_outline),
                  validator: (value) {
                    if (value.isEmpty) return "Le champ Email est obligatoire";
                  },
                  onSaved: (value) {
                    _email = value;
                  },
                ),
              ),

              new Padding(
                padding: EdgeInsets.only(top:14, bottom: 14, left:28, right: 28),
                child: new TextFormField(
                  initialValue: _pseudo,
                  style: PlaygroundLoginTextFieldStyle.getStyle(context),
                  decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Pseudo", Icons.person),
                  validator: (value) {
                    if (value.isEmpty) return "Le champ Pseudo est obligatoire";
                  },
                  onSaved: (value) {
                    _pseudo = value;
                  },
                ),
              ),

              new Padding(
                padding: EdgeInsets.only(top:14, bottom: 14, left:28, right: 28),
                child: new DateTimePickerFormField(
                  style: PlaygroundLoginTextFieldStyle.getStyle(context),
                  decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Date de naissance", Icons.cake),
                  format: new DateFormat("dd/MM/yyyy"),
                  dateOnly: true,
                  validator: (value)
                  {
                    if (value == null) return "Le champ date de naissance est obligatoire";
                  }
                ),
              ),

              new Padding(
                padding: EdgeInsets.only(top:14, bottom: 14, left:28, right: 28),
                child: new TextFormField(
                  initialValue: _mdp,
                  obscureText: true,
                  style: PlaygroundLoginTextFieldStyle.getStyle(context),
                  decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Mot de passe", Icons.vpn_key),
                  validator: (value) {
                    if (value.isEmpty) return "Le champ Mot de passe est obligatoire";
                  },
                  onSaved: (value) {
                    _mdp = value;
                  },
                ),
              ),

              new Padding(
                padding: EdgeInsets.only(top:14, bottom: 14, left:28, right: 28),
                child: new TextFormField(
                  initialValue: _mdp_confirmation,
                  obscureText: true,
                  style: PlaygroundLoginTextFieldStyle.getStyle(context),
                  decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Confirmation mot de passe", Icons.vpn_key),
                  validator: (value) {
                    if (value.isEmpty) return "Le champ Mot de passe est obligatoire";
                    if (value != _mdp && _mdp.isNotEmpty) return "Les mots de passe ne correspondent pas";
                  },
                  onSaved: (value) {
                    _mdp_confirmation = value;
                  },
                ),
              ),

              new Padding(
                  padding: EdgeInsets.only(top:4, bottom: 20, left:24, right: 24),
                  child:
                  new Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[

                      new Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[

                          new PlaygroundCheckbox(
                            value: _cgu_accepted,
                            dark: true,
                            onChanged: (value) {
                              if (value == null) value = false;
                              setState(() {
                                _cgu_accepted = value;
                              });
                            },
                          ),

                          GestureDetector(
                            child: new Text(
                              "J'accepte les conditions d'utilisation",
                              style: new TextStyle(
                                  color: Colors.white,
                                  fontSize: 16
                              ),
                            ),
                            onTap: () {
                              Navigator.pushNamed(context, '/cgu');
                            },
                          )

                        ],
                      ),

                      (_cgu_accept_message.isNotEmpty) ?
                      new Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          new Padding(
                            padding: EdgeInsets.only(right: 8),
                            child: new Icon(Icons.error_outline, color: Theme.of(context).primaryColorDark),
                          ),
                          new Text(
                            _cgu_accept_message,
                            style: new TextStyle(
                                color: Theme.of(context).primaryColorDark
                            ),
                          ),
                        ]
                      ) :
                      new Material()
                    ],
                  )

              ),

              new Padding(
                padding: EdgeInsets.only(top:18, bottom: 18, left:28, right: 28),
                child: new MaterialButton(
                    child: new Text(
                        "Valider",
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

            ],
          ),
        ),
          )
        )
      )

    );
  }

}
