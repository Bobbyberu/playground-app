
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/User.dart';

class AuthController extends CommonController {

  static const String route = CommonController.baseUrl + "users/";

  Future checkConnection() {
    return get(route + "check");
  }

  ///
  /// Send credential to api to receive a JWT
  ///
  Future postCredentials(String email, String password) {
    Map<String,String> credentials = new Map();
    credentials["mail"] = email;
    credentials["password"] = password;

    return post(route + "login", credentials);
  }

  Future getUserByUsername(String username) {
    return get(route + "username/" + username);
  }

  Future getUserByMail(String username) {
    return get(route + "mail/" + username);
  }

  ///
  /// send user object to the signup api to create user account
  ///
  Future postUser(User user) {
    return post(route + "signup", user.toJson());
  }

  ///
  /// Call logout api to invalidate JWT
  ///
  Future logout() {
    return post(route + "logout", new Map<String,String>());
  }
}