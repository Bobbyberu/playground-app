
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/entities/User.dart';

class UserController extends CommonController {

  static const String route = CommonController.baseUrl + "users/";

  Future putUser(User user) {
    return put(route +  SessionManager.getInstance().getUser().id.toString(), user.toJson());
  }

  Future putPlaygroundFavorite(int playgroundId) {
    return put(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlaygrounds/" + playgroundId.toString(), {});
  }

  Future getFavouritePlaygrounds() {
    return get(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlaygrounds/");
  }

  Future getPlaygroundFavorite(int playgroundId) {
    return get(route + SessionManager.getInstance().getUser().id.toString() + "/favouritePlaygrounds/" + playgroundId.toString());
  }

}
