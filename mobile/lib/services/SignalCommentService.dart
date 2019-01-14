
import 'package:Playground/controllers/SignalCommentController.dart';
import 'package:Playground/entities/SignalComment.dart';

class SignalCommentService {

  SignalCommentController _controller = new SignalCommentController();

  Future<bool> save(SignalComment signal) async{
    bool res = false;

    await _controller.postSignalComment(signal).then((response) {
      res = response.statusCode != null && (response.statusCode == 201 || response.statusCode == 200);
    }).catchError((error){
      _controller.printError(error);
      res= false;
    });

    return res;
  }

}