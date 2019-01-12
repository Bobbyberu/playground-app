
import 'dart:io';

import 'package:Playground/controllers/CommonController.dart';

class ImageController extends CommonController {

  static const String route = CommonController.baseUrl + "images/";

  //http://gitlab.mooreacreations.com/moorea/cosportners-app/blob/master/web/src/app/profil/profil.component.ts
  Future postPlaygroundImage(int playgroundId, File img) {
    return postFile(route + "playground/" + playgroundId.toString(), img);
  }

  Future getPlaygroundImage(int playgroundId) {
    return get(route + "playground/" + playgroundId.toString());
  }

}