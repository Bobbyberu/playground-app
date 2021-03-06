
import 'dart:convert';

import 'package:Playground/controllers/AuthController.dart';
import 'package:Playground/entities/User.dart';
import 'package:corsac_jwt/corsac_jwt.dart';

class SessionManager {

  static SessionManager _instance;

  String mail;
  User user;

  static SessionManager getInstance() {
    if(_instance == null) {
      _instance = new SessionManager();
    }
    return _instance;
  }

  void setUser(User user) {
    this.user = user;
  }

  void setMail(String mail) {
    this.mail = mail;
  }

   User getUser(){
    return user;
  }

  Future loadUser(String token) async {
    AuthController controller = new AuthController();
    JWT jwt = JWT.parse(token.split(" ").last);
    SessionManager.getInstance().setMail(jwt.subject);

    await controller.getUserByMail(jwt.subject).then((response) {
      if (response.statusCode == 200) {
        User loggedUser = User.fromJson(json.decode(response.body));
        SessionManager.getInstance().setUser(loggedUser);
      }
    }).catchError((error) {
      controller.printError(error);
    });
  }

  void clearSession() {
    user = null;
    mail = null;
  }

}