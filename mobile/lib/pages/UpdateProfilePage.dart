
import 'dart:io';

import 'package:Playground/entities/User.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/services/UserService.dart';
import 'package:Playground/validators/EmailValidator.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLoginTextFieldStyle.dart';
import 'package:Playground/widgets/user/UserAvatarCircle.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';

class UpdateProfilePage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => UpdateProfilePageState();

}

class UpdateProfilePageState extends State<UpdateProfilePage> {

  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  final GlobalKey<FormState> _passwordFormKey = new GlobalKey<FormState>();
  final UserService userService = new UserService();

  bool _isCoordFormLoading;
  bool _ispasswordLoading;
  bool _isAvatarLoading;

  User updatedUser;

  String _newPassword;
  String _confNewPassword;

  @override
  void initState() {
    super.initState();
    _isAvatarLoading = false;
    _ispasswordLoading = false;
    _isCoordFormLoading = false;
    updatedUser = SessionManager.getInstance().getUser();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: Text("Modification profil")
      ),
      body: SingleChildScrollView(
        child: ConstrainedBox(
          constraints: BoxConstraints(),
          child: Material(
            child:  Padding(
              padding: EdgeInsets.only(top:12, left: 12, right: 12, bottom: 32),
              child: Column(
                children: <Widget>[

                  Card(
                    child: Padding(
                      padding: EdgeInsets.all(12),
                      child: new Container(
                          constraints: new BoxConstraints(minWidth: MediaQuery.of(context).size.width),
                          child: Stack(
                            alignment: Alignment(0, 0),
                            children: <Widget>[
                              (_isAvatarLoading) ?
                              new Container(
                                height: 100, width: 100,
                                decoration: new BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(width: 4, color: Theme.of(context).primaryColor),
                                  color: Colors.grey,
                                )
                              )
                              : UserAvatarCircle(user: updatedUser, size: 100),
                              Container(
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  color: Color.fromARGB(50, 0, 0, 0)
                                ),
                                child: IconButton(
                                  iconSize: 50,
                                  icon: Icon(Icons.camera_enhance, color: Colors.white),
                                  onPressed: () async {
                                    File selectedImg = await ImagePicker.pickImage(source: ImageSource.gallery);
                                    if (selectedImg != null) {
                                      setState(() {
                                        _isAvatarLoading = true;
                                      });
                                      userService.uploadAvatar(selectedImg).then((response) {
                                        setState(() {_isAvatarLoading = false;});
                                      });
                                    }
                                  }
                                )
                              )
                            ],
                          )
                      )
                    )
                  ),

                  Form(
                    key: _formKey,
                    child: Padding(
                      padding: EdgeInsets.only(top: 24, bottom: 12),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[

                          Padding(
                              padding: EdgeInsets.only(left: 12),
                              child: new Text("Pseudo", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                              padding: EdgeInsets.all(12),
                              child: new TextFormField(
                                initialValue: updatedUser.username,
                                style: PlaygroundTextFieldStyle.getStyle(context),
                                decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                                validator : (value) {
                                  if(value.isEmpty) return "Le champ Pseudo est obligatoire";
                                },
                                onSaved: (value) {
                                  updatedUser.username = value;
                                }
                              )
                          ),

                          Padding(
                              padding: EdgeInsets.only(left: 12),
                              child: new Text("Date de naissance", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                            padding: EdgeInsets.all(12),
                            child: new DateTimePickerFormField(
                              initialValue: updatedUser.birthDate,
                              style: PlaygroundTextFieldStyle.getStyle(context),
                              decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                              format: new DateFormat("dd/MM/yyyy"),
                              dateOnly: true,
                              validator: (value)
                              {
                                if (value == null) return "Le champ date de naissance est obligatoire";
                              },
                              onSaved: (value) {
                                updatedUser.birthDate = value;
                              },
                            ),
                          ),

                          Padding(
                            padding: EdgeInsets.only(left: 12),
                            child: new Text("Email", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                            padding: EdgeInsets.all(12),
                            child: new TextFormField(
                              initialValue: updatedUser.mail,
                              style: PlaygroundTextFieldStyle.getStyle(context),
                              decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                              validator : (value) {
                                if(value.isEmpty) return "Le champ Email est obligatoire";
                                if(!EmailValidator.isEmail(value)) return "Le format de l'Email n'est pas valide";
                              },
                              onSaved: (value) {
                                updatedUser.mail = value;
                              }
                            )
                          ),

                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              Padding(
                                  padding: EdgeInsets.all(12),
                                  child:
                                  (_isCoordFormLoading) ?
                                  new CircularProgressIndicator(
                                    valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor),
                                  )
                                  : PlaygroundButton(
                                      "Enregistrer",
                                      validateForm
                                  )
                              )
                            ],
                          )

                        ],
                      ),
                    )
                  ),

                  Divider(),

                  new Form(
                    key: _passwordFormKey,
                    child: Padding(
                      padding: EdgeInsets.only(top: 12, bottom: 12),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[

                          Padding(
                              padding: EdgeInsets.only(left: 12),
                              child: new Text("Ancien mot de passe", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                              padding: EdgeInsets.all(12),
                              child: new TextFormField(
                                  obscureText: true,
                                  style: PlaygroundTextFieldStyle.getStyle(context),
                                  decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                                  validator : (value) {
                                    if(value.isEmpty) return "Ce champ est obligatoire";
                                  },
                                  onSaved: (value) {
                                    //TODO
                                  }
                              )
                          ),

                          Padding(
                              padding: EdgeInsets.only(left: 12),
                              child: new Text("Nouveau mot de passe", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                              padding: EdgeInsets.all(12),
                              child: new TextFormField(
                                  obscureText: true,
                                  style: PlaygroundTextFieldStyle.getStyle(context),
                                  decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                                  validator : (value) {
                                    _newPassword = value;
                                    if(value.isEmpty) return "Ce champ est obligatoire";
                                  },
                                  onSaved: (value) {
                                    _newPassword = value;
                                  }
                              )
                          ),

                          Padding(
                              padding: EdgeInsets.only(left: 12),
                              child: new Text("Confirmation du nouveau mot de passe", style: PlaygroundLabelStyle.getStyle(context))
                          ),
                          Padding(
                              padding: EdgeInsets.all(12),
                              child: new TextFormField(
                                  obscureText: true,
                                  style: PlaygroundTextFieldStyle.getStyle(context),
                                  decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                                  validator : (value) {
                                    if(value.isEmpty) return "Ce champ est obligatoire";
                                    if(value != _newPassword) return "Les mots de passe ne correspondent pas";
                                  },
                                  onSaved: (value) {
                                    _confNewPassword = value;
                                  }
                              )
                          ),

                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: <Widget>[
                              Padding(
                                  padding: EdgeInsets.all(12),
                                  child:
                                  (_ispasswordLoading) ?
                                  new CircularProgressIndicator(
                                    valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor),
                                  )
                                  : PlaygroundButton(
                                      "Changer mot de passe",
                                      validatePasswordForm
                                  )
                              )
                            ],
                          )


                        ],
                      ),
                    ),
                  )


                ],
              ),
            )
          ),
        )
      ),
    );
  }


  void validateForm() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();

      //TODO check username and email availability
      setState(() {
        _isCoordFormLoading = true;
      });
      await userService.updateUser(updatedUser).then((response){
        if (response) {
          PlaygroundDialog.showValidDialog(context, "Mise à jour du profil", "Vos informations ont bien été mises à jour", () { Navigator.of(context).pop(); });
        } else {
          PlaygroundDialog.showErrorDialog(context, "Mise à jour du profil", "Un problème est survenu lors de l'enregistrement. Veuillez réessayer plus tard", () { Navigator.of(context).pop(); });
        }
        setState(() {
          _isCoordFormLoading = false;
        });
      });

    }
  }

  void validatePasswordForm() async {
    if (_passwordFormKey.currentState.validate()) {
      _passwordFormKey.currentState.save();

      //TODO Check old password

      setState(() {
        _ispasswordLoading = true;
      });
      if (_newPassword == _confNewPassword) {
        await userService.changePassword(_newPassword).then((response) {
          if (response) {
            PlaygroundDialog.showValidDialog(context, "Changement de votre mot de passe", "Vos mot de passe ont bien été mis à jour", () { Navigator.of(context).pop(); });
          } else {
            PlaygroundDialog.showErrorDialog(context, "Changement de votre mot de passe", "Un problème est survenu lors de l'enregistrement du nouveau mot de passe. Veuillez réessayer plus tard", () { Navigator.of(context).pop(); });
          }
          setState(() {
            _ispasswordLoading = false;
          });
        });
      }

    }
  }

}