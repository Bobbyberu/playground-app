
import 'package:Playground/controllers/ReportCommentController.dart';
import 'package:Playground/entities/ReportComment.dart';

class ReportCommentService {

  ReportCommentController _controller = new ReportCommentController();

  Future<bool> save(ReportComment signal) async{
    bool res = false;

    await _controller.postSignalComment(signal).then((response) {
      _controller.printResponse(response);
      res = response.statusCode != null && (response.statusCode == 201 || response.statusCode == 200);
    }).catchError((error){
      _controller.printError(error);
      res= false;
    });

    return res;
  }

}