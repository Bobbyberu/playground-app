
import 'package:http/http.dart' as http;

import 'package:Playground/controllers/AuthController.dart';

class AuthService {

  static String token = "";

  AuthController _controller = new AuthController();

  ///
  ///Call server auth login method to get a JWT
  ///return bool (success)
  ///
  Future<bool> login(String email, String password) async {
    bool res = false;

    await _controller.postCredentials(email, password).then((response){
      http.Response resp = response as http.Response;

      res = response.statusCode == 200;
      var headers = response.headers as Map<String,String>;

      if(headers.containsKey("authorization")) {
        AuthService.token = headers["authorization"];
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
  Future<bool> signup() async {
    //TODO
    return true;
  }

  ///
  ///Disconnect user from the application
  ///
  void logout() async {
    // TODO
  }

}