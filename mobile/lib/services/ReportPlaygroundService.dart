
import 'package:Playground/controllers/ReportPlaygroundController.dart';
import 'package:Playground/entities/ReportPlayground.dart';

class ReportPlaygroundService {

  ReportPlaygroundController _controller = new ReportPlaygroundController();

  Future<bool> saveSignalPlayground(ReportPlayground reportPlayground) async{
    bool res = false;

    await _controller.postSignalPlayground(reportPlayground).then((response) {
      res = response.statusCode == 201 || response.statusCode == 200;
    }).catchError((error) {
      _controller.printError(error);
      res = false;
    });

    return res;
  }

}