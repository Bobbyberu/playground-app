
import 'dart:convert';
import 'dart:io';
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/controllers/PlaygroundController.dart';
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/entities/User.dart';

import 'package:http/http.dart' as http;

class PlaygroundService {

  static String getPlaygroundImageUrl(Playground playground) {
    return CommonController.baseUrl + "playgrounds/" + playground.id.toString() + "/image";
  }

  PlaygroundController _controller = new PlaygroundController();

  ///
  ///Save a playground to the database
  ///return bool success
  ///
  Future<bool> save(Playground playground, File img) async {
    bool res = false;

    await _controller.postPlayground(playground).then((response) async {
      http.Response resp = response as http.Response;
      res = response.statusCode == 201 || response.statusCode == 200;
      Playground pgCreated = Playground.fromJson(json.decode(response.body));
      await _controller.postImage(pgCreated.id, img);
    }).catchError((error) {
      _controller.printError(error);
    });

    return res;
  }

  ///
  ///Retrieve a list of all the playground near the user location
  ///return List of playgrounds
  ///
  Future<List<Playground>> getPlaygroundsNearMe() async {
    List<Playground> playgrounds = new List<Playground>();

    await _controller.getAllPlaygrounds().then((response){
      if(response.statusCode == 200){
        List<dynamic> playgroundJson = json.decode(response.body);

        playgroundJson.forEach((pg) {
          Playground playground = Playground.fromJson(pg);
          playgrounds.add(playground);
        });
      }
    }).catchError((error){
      _controller.printError(error);
    });


    return playgrounds;
  }


  ///
  /// Retrieve playground by Id
  /// return Playground object
  ///
  Future<Playground> getPlaygroundById(int id) async {
    Playground playground;

    await _controller.getPlaygroundById(id).then((response) {
      if(response.statusCode == 200) {
        playground = Playground.fromJson(json.decode(response.body));
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return playground;
  }

  ///
  /// Retrieve a list of playgrounds by search terms
  /// return list of playgrounds
  ///
  Future<List<Playground>> search(String search) async {

    List<Playground> playgrounds = new List<Playground>();

    await _controller.getPlaygroundsBySearch(search).then((response) {

      if (response.statusCode != null && response.statusCode == 200) {
        List<dynamic> playgroundsJson = json.decode(response.body);
        
        playgroundsJson.forEach((value){
          Playground p = Playground.fromJson(value as Map<String, dynamic>);
          playgrounds.add(p);
        });
      }
    }).catchError((error){
      _controller.printError(error);
    });

    return playgrounds;
  }

  Future<List<User>> getPlayingUser(int playgroundId) async{
    List<User> players = new List();

    await _controller.getPlayersOfPlayground(playgroundId).then((response){
      if (response.statusCode != null && response.statusCode == 200) {
        print(response.body);
        List<dynamic> playersJson = json.decode(response.body);

        playersJson.forEach((u) {
          User user = User.fromJson(u as Map<String, dynamic>);
          players.add(user);
        });
      }
    });

    return players;
  }

  Future<bool> addPlayerInPlayground(Playground playground, User player, Sport sport) async {
    bool success = false;

    await _controller.putPlayerInPlayground(playground.id, player.id, sport.id).then((response) {
      _controller.printResponse(response);
      success = response.statusCode == 200;
    }).catchError((error){
      _controller.printError(error);
    });

    return success;
  }

  Future<bool> addPlayerOutPlayground(Playground playground, User player) async {
    bool success = false;

    await _controller.putPlayerOutPlayground(playground.id, player.id).then((response) {
      _controller.printResponse(response);
      success = response.statusCode == 200;
    }).catchError((error){
      _controller.printError(error);
    });

    return success;
  }

}