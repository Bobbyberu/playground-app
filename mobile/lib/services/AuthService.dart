
import 'package:Playground/entities/User.dart';
import 'package:Playground/services/TokenManager.dart';
import 'package:http/http.dart' as http;

import 'package:Playground/controllers/AuthController.dart';

class AuthService {

  AuthController _controller = new AuthController();

  ///
  ///Call server auth login method to get a JWT
  ///return bool (success)
  ///
  Future<bool> login(String email, String password) async {
    bool res = false;

    await _controller.postCredentials(email, password).then((response) async {
      print(response.statusCode);
      res = response.statusCode == 200;
      print(res);
      var headers = response.headers as Map<String,String>;
      print("headers parsed");

      if(headers.containsKey("authorization")) {
        print("setting token " + headers["authorization"]);
        TokenManager.getInstance().setToken(headers["authorization"]);
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return res;
  }

  ///
  ///Create a new user account
  ///return bool (success)
  ///
  Future<bool> signUp(User newUser) async {
    bool res = false;

    await _controller.postUser(newUser).then((response) async {
      print(response.statusCode);
      res = response.statusCode == 200;
    }).catchError((error){
      _controller.printError(error);
    });

    return res;
  }

  ///
  /// Disconnect user from the application
  /// Clear JWT
  ///
  void logout() async {
    await _controller.logout().then((response) {
      TokenManager.getInstance().cleanToken();
    }).catchError((error) {
      _controller.printError(error);
    });
  }

}