
import 'package:Playground/controllers/SignalPlaygroundController.dart';
import 'package:Playground/entities/SignalPlayground.dart';

class SignalPlaygroundService {

  SignalPlaygroundController _controller = new SignalPlaygroundController();

  Future<bool> saveSignalPlayground(SignalPlayground signalPlayground) async{
    bool res = false;

    await _controller.postSignalPlayground(signalPlayground).then((response) {
      res = response.statusCode == 201 || response.statusCode == 200;
    }).catchError((error) {
      _controller.printError(error);
      res = false;
    });

    return res;
  }

}