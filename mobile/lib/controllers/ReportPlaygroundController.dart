
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/ReportPlayground.dart';

class ReportPlaygroundController extends CommonController {

  static const String route = CommonController.baseUrl + "reportPlaygrounds/";

  Future postSignalPlayground(ReportPlayground signal) {
    return post(CommonController.baseUrl + "playgrounds/" + signal.playground.id.toString() + "/reportPlaygrounds", signal.toJson());
  }

}