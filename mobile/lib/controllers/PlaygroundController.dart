
import 'dart:io';

import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/Playground.dart';
import 'dart:convert';


class PlaygroundController extends CommonController{

  static const String route = CommonController.baseUrl + "playgrounds/";

  Future getAllPlaygrounds() async {
    return get(Uri.encodeFull(route));
  }

  Future getPlaygroundById(int id) async {
     return get(Uri.encodeFull(route + id.toString()));
  }

  Future getCommentsOfPlayrgound(int playgroundId) {
    return get(route + playgroundId.toString() + "/comments");
  }

  Future getPlaygroundsBySearch(String keywords) {
    return get(route + "search/" + Uri.encodeFull(keywords));
  }

  Future postPlayground(Playground playground) async {
    return post(route, playground.toJson());
  }

  Future postImage(int playgroundId, File file) {
    Uri url = Uri.http(CommonController.domain, "/api/playgrounds/" + playgroundId.toString() + "/image");
    return postFile(url, file);
  }

}