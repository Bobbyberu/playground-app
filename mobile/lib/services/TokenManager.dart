
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class TokenManager {

  static TokenManager _instance;
  static const String _key = "playgroundJWT";

  FlutterSecureStorage storage = new FlutterSecureStorage();
  String _token ;


  static TokenManager getInstance() {
    if (_instance == null) {
      _instance =  new TokenManager();
    }
    return _instance;
  }

  Future<String> getToken() async{
    if(_token == null) {
      _token = await storage.read(key: _key);
    }
    return _token;
  }

  void setToken(String token) async {
    _token = _token;
    await storage.write(key: _key, value: token);
  }

  void cleanToken() async {
    await storage.delete(key: _key);
  }

}