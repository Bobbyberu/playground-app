
import 'dart:convert';
import 'dart:io';
import 'package:Playground/controllers/PlaygroundController.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';

import 'package:http/http.dart' as http;

class PlaygroundService {

  PlaygroundController _controller = new PlaygroundController();

  ///
  ///Save a playground to the database
  ///return bool success
  ///
  Future<bool> save(Playground playground, File img) async {
    bool res = false;

    await _controller.postPlayground(playground).then((response) {
      http.Response resp = response as http.Response;
      res = response.statusCode == 201 || response.statusCode == 200;
    }).catchError((error) {
      _controller.printError(error);
    });

    return res;
  }

  ///
  ///Retrieve a list of all the playground near the user location
  ///return List of playgrounds
  ///
  List<Playground> getPlaygroundsNearMe() {

    Playground playground1 = Playground.getDefault();
    playground1.id = 2;
    playground1.name = "Playground 1";
    playground1.city = "Lyon";
    playground1.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
    playground1.imgPath = "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.oxforddictionaries.com%2Fwp-content%2Fuploads%2Fmountain-names.jpg&f=1";
    playground1.address = "25 Impasse des marroniers";
    playground1.latitude = 45.764045;
    playground1.longitude = 4.835675;
    playground1.sports = new Set<Sport>();
    playground1.sports.add(Sport.createNew(1,"Basketball", ""));
    playground1.sports.add(Sport.createNew(3,"Handball", ""));
    playground1.sports.add(Sport.createNew(4,"Volleyball", ""));

    Playground playground2 = new Playground.getDefault();
    playground2.name = "Playground 2";
    playground2.address = "Lyon";
    playground2.address = "25 Impasse des marroniers";
    playground2.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fi0.wp.com%2Ftechbeasts.com%2Fwp-content%2Fuploads%2F2016%2F12%2FNature-Mountain-HD-Wallpapers.jpg&f=1";
    playground2.latitude = 45.746778;
    playground2.longitude = 4.824748;

    Playground playground3 = new Playground.getDefault();
    playground3.city = "Villeurbanne";
    playground3.address = "25 Impasse des marroniers";
    playground3.name = "Playground 3";
    playground3.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.Kn6serGrBlmmo6rgxGloGwHaEK%26pid%3D15.1&f=1";
    playground3.latitude = 45.752583;
    playground3.longitude = 4.842777;

    List<Playground> playgrounds = new List<Playground>();
    playgrounds.add(playground1);
    playgrounds.add(playground2);
    playgrounds.add(playground3);

    return playgrounds;
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

}