
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/SignalComment.dart';

class SignalCommentController extends CommonController {

  static const String route = CommonController.baseUrl + "signalComments/";

  Future postSignalComment(SignalComment signal) {
    return post(route, signal.toJson());
  }

}