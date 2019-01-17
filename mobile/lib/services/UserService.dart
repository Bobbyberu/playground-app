
import 'dart:convert';

import 'package:Playground/controllers/UserController.dart';
import 'package:Playground/entities/Playground.dart';

class UserService {

  UserController _controller = new UserController();

  Future<Set<Playground>> getFavouritesPlaygrounds() async {
    Set<Playground> playgrounds = new Set();

    await _controller.getFavouritePlaygrounds().then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        List<dynamic> playgroundsJson = json.decode(response.body.toString());
        playgroundsJson.forEach((p) {
          Playground pg = Playground.fromJson(p);
          playgrounds.add(pg);
        });
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return playgrounds;
  }

  Future<bool> checkIfFavorite(Playground playground) async {
    bool favorite = false;

    await _controller.getPlaygroundFavorite(playground.id).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        favorite = json.decode(response.body) as bool;
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return favorite;
  }

  Future<bool> togglePlaygroundFavorite(Playground playground) async {
    bool favorite = false;

    await _controller.putPlaygroundFavorite(playground.id).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        favorite = json.decode(response.body) as bool;
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return favorite;
  }



}