
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/entities/User.dart';

class UserController extends CommonController {

  static const String route = CommonController.baseUrl + "users/";

  Future putPlaygroundFavorite(int playgroundId) {
    return put(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlayground/" + playgroundId.toString(), {});
  }

  Future getFavouritePlaygrounds() {
    return get(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlayground/");
  }

  Future getPlaygroundFavorite(int playgroundId) {
    return get(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlayground/" + playgroundId.toString());
  }

}