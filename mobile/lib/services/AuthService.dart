
import 'package:Playground/entities/User.dart';
import 'package:Playground/enums/ConnectionStatus.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/services/TokenManager.dart';

import 'package:Playground/controllers/AuthController.dart';

class AuthService {

  AuthController _controller = new AuthController();

  ///
  /// Start a test call to the server API to check if the server is available or if the user is authenticated
  ///
  Future<ConnectionStatus> checkConnection() async {
    ConnectionStatus res;

    await _controller.checkConnection().then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        res = ConnectionStatus.AUTHENTICATED;
      }
      else if (response.statusCode != null && response.statusCode == 403) {
        res = ConnectionStatus.NOT_AUTHENTICATED;
      }
      else {
        res = ConnectionStatus.SERVER_UNAVAILABLE;
      }
    }).catchError((error) {
      res = ConnectionStatus.SERVER_UNAVAILABLE;
    });

    return res;
  }

  ///
  ///Call server auth login method to get a JWT
  ///return bool (success)
  ///
  Future<bool> login(String email, String password) async {
    bool res = false;

    await _controller.postCredentials(email, password).then((response) async {
      _controller.printResponse(response);
      res = response.statusCode == 200;
      var headers = response.headers as Map<String,String>;

      if(headers.containsKey("authorization")) {
        var token =  headers["authorization"];
        TokenManager.getInstance().setToken(token);
        await SessionManager.getInstance().loadUser(token);
      }
    }).catchError((error) {
      _controller.printError(error);
      res = false;
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
      res = response.statusCode == 201;
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
      SessionManager.getInstance().clearSession();
    }).catchError((error) {
      _controller.printError(error);
    });
  }

}