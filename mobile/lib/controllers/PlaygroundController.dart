
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/Playground.dart';
import 'dart:convert';


class PlaygroundController extends CommonController{

  static const String route = CommonController.baseUrl + "playgrounds/";

  Future getPlaygroundById(int id) async {
     return get(Uri.encodeFull(route + id.toString()));
  }

  Future getPlaygroundsBySearch(String keywords) {
    return get(route + "search/?keywords=" + Uri.encodeFull(keywords));
  }

  Future postPlayground(Playground playground) async {
    return post(route, playground.toJson());
  }

}