
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/SignalPlayground.dart';

class SignalPlaygroundController extends CommonController {

  static const String route = CommonController.baseUrl + "signalPlaygrounds/";

  Future postSignalPlayground(SignalPlayground signal) {
    return post(route, signal.toJson());
  }

}