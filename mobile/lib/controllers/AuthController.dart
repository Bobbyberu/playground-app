
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/User.dart';

class AuthController extends CommonController {

  static const String route = CommonController.baseUrl + "users/";

  ///
  /// Send credential to api to receive a JWT
  ///
  Future postCredentials(String email, String password) {
    Map<String,String> credentials = new Map();
    credentials["username"] = email;
    credentials["password"] = password;

    return post(route + "login", credentials);
  }

  Future getUserByUsername(String username) {
    return get(route + "name/" + username);
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