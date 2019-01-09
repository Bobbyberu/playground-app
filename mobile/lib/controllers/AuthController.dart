
import 'package:Playground/controllers/CommonController.dart';

class AuthController extends CommonController {

  static const String route = CommonController.baseUrl + "users/";

  ///
  ///
  ///
  Future postCredentials(String email, String password) {
    Map<String,String> credentials = new Map();
    credentials["username"] = email;
    credentials["password"] = password;

    return post(route + "login", credentials);
  }

}