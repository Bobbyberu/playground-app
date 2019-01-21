
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/ReportComment.dart';

class ReportCommentController extends CommonController {

  static const String route = CommonController.baseUrl + "signalComments/";

  Future postSignalComment(ReportComment signal) {
    return post("/comments/" + signal.comment.id.toString() + "/reportComments", signal.toJson());
  }

}