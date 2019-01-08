
import 'dart:math';

class AuthService {

  /**
   * Call server auth login method to get a JWT
   * return bool (success)
   */
  Future<bool> login(String email, String password) async {
    //TODO call ws
    Random r = new Random();
    int s = r.nextInt(2);
    return s == 1;
  }

  /**
   * Create a new user account
   * return bool (success)
   */
  Future<bool> signup() async {
    //TODO
    Random r = new Random();
    int s = r.nextInt(2);
    return s == 1;
  }

  /**
   * Disconnect user from the application
   */
  void logout() async {
    // TODO
  }

}