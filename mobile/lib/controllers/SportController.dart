
import 'package:Playground/controllers/CommonController.dart';

class SportController extends CommonController {

  static const String route = CommonController.baseUrl + "sports/";

  ///
  /// Retrieve all sports
  ///
  Future getAllSports() {
    return get(route);
  }

}