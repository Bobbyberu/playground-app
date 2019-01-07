
import 'dart:math';

class AuthService {

  Future<bool> login(String email, String password) async {
    //TODO call ws
    Random r = new Random();
    int s = r.nextInt(2);
    return s == 1;
  }

  Future<bool> signup() async {
    //TODO
    Random r = new Random();
    int s = r.nextInt(2);
    return s == 1;
  }

  void logout() async {
    // TODO
  }

}