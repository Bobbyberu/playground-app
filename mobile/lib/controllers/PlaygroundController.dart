
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/Playground.dart';


class PlaygroundController extends CommonController{

  static const String route = "playgrounds/";

  Future<Playground> getPlaygroundById(int id) async {
     get(route + id.toString()).then(
         (value) {

         }
     );
  }

}