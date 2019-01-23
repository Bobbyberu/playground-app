
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/ReportPlayground.dart';

class ReportPlaygroundController extends CommonController {

  static const String route = CommonController.baseUrl + "reportPlaygrounds/";

  Future postSignalPlayground(ReportPlayground signal) {
    return post("/playgrounds/" + signal.playground.toString() + "/reportPlaygrounds", signal.toJson());
  }

}