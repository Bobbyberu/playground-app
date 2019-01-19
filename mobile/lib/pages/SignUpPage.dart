import 'package:Playground/entities/User.dart';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/validators/EmailValidator.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundCheckbox.dart';
import 'package:Playground/widgets/style/PlaygroundLoginTextFieldStyle.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

///
///Widget page to display sign up form
///
class SignUpPage extends StatefulWidget {

  //TODO check if user already connected ==> redirect to home

  @override
  State<StatefulWidget> createState() => SignUpPageState();

}

class SignUpPageState extends State<SignUpPage> {

  String _email;
  String _pseudo;
  DateTime _birthDate;
  String _mdp;
  String _mdpConfirmation;
  bool _cguAccepted;
  String _cguAcceptMessage;

  AuthService _authService;
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();

  @override
  void initState() {
    _email = "";
    _pseudo = "";
    _birthDate = DateTime.now();
    _mdp = "";
    _mdpConfirmation = "";
    _cguAccepted = false;
    _cguAcceptMessage = "";

    _authService = new AuthService();
    super.initState();
  }

  ///
  /// Validate and save form
  /// call ws to save new user in database
  /// Display dialog to show success / failure
  ///
  void validateForm() async {
    (!_cguAccepted) ? setState(() { _cguAcceptMessage = "Vous devez accepter les CGU"; }) : setState(() { _cguAcceptMessage = ""; }) ;
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();
      if (_cguAccepted) {
        User newUser = new User(
          mail: _email,
          username: _pseudo,
          password: _mdp,
          birthDate: _birthDate
        );

        bool result = await _authService.signUp(newUser);

        if (result) {
          PlaygroundDialog.showValidDialog(
              context,
              "Votre inscription est terminée !",
              "Un email de confirmation va vous être envoyé",
              () {Navigator.pushReplacementNamed(context, '/');}
          );
        } else {
          PlaygroundDialog.showErrorDialog(
              context,
              "Erreur à la validation",
              "Un problème est survenu lors de la validation. Veuillez réessayer plus tard.",
                  () {Navigator.pop(context);}
          );
        }
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

              child: new Padding(
                padding: EdgeInsets.only(top: 22),
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
                          if (!EmailValidator.isEmail(value)) return "L'email n'est pas valide";
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
                          },
                          onSaved: (value) {
                            _birthDate = value;
                          },
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
                        initialValue: _mdpConfirmation,
                        obscureText: true,
                        style: PlaygroundLoginTextFieldStyle.getStyle(context),
                        decoration: PlaygroundLoginTextFieldStyle.getDecoration(context, "Confirmation mot de passe", Icons.vpn_key),
                        validator: (value) {
                          if (value.isEmpty) return "Le champ Mot de passe est obligatoire";
                          if (value != _mdp && _mdp.isNotEmpty) return "Les mots de passe ne correspondent pas";
                        },
                        onSaved: (value) {
                          _mdpConfirmation = value;
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
                                  value: _cguAccepted,
                                  dark: true,
                                  onChanged: (value) {
                                    if (value == null) value = false;
                                    setState(() {
                                      _cguAccepted = value;
                                    });
                                  },
                                ),

                                new Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: <Widget>[
                                    Text(
                                      "J'accepte les ",
                                      style: new TextStyle(
                                          color: Colors.white,
                                          fontSize: 16
                                      ),
                                    ),
                                    GestureDetector(
                                      child: new Text(
                                        "conditions d'utilisation",
                                        style: new TextStyle(
                                            color: Colors.white,
                                            fontSize: 16,
                                            decoration: TextDecoration.underline
                                        ),
                                      ),
                                      onTap: () {
                                        Navigator.pushNamed(context, '/cgu');
                                      },
                                    )
                                  ],
                                ),


                              ],
                            ),

                            (_cguAcceptMessage.isNotEmpty) ?
                            new Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: <Widget>[
                                  new Padding(
                                    padding: EdgeInsets.only(right: 8),
                                    child: new Icon(Icons.error_outline, color: Theme.of(context).primaryColorDark),
                                  ),
                                  new Text(
                                    _cguAcceptMessage,
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

            ),
          )
        )
      )

    );
  }

}
