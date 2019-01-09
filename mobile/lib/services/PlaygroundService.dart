
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

    Playground playground1 = new Playground();
    playground1.name = "Playground 1";
    playground1.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
    playground1.imgPath = "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.oxforddictionaries.com%2Fwp-content%2Fuploads%2Fmountain-names.jpg&f=1";
    playground1.address = "25 Impasse des marroniers";
    playground1.latitude = 45.764045;
    playground1.longitude = 4.835675;
    playground1.sports.add(Sport.createNew(1,"Basketball"));
    playground1.sports.add(Sport.createNew(3,"Handball"));
    playground1.sports.add(Sport.createNew(4,"Volleyball"));

    Playground playground2 = new Playground();
    playground2.name = "Playground 2";
    playground2.address = "25 Impasse des marroniers";
    playground2.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fi0.wp.com%2Ftechbeasts.com%2Fwp-content%2Fuploads%2F2016%2F12%2FNature-Mountain-HD-Wallpapers.jpg&f=1";
    playground2.latitude = 45.746778;
    playground2.longitude = 4.824748;

    Playground playground3 = new Playground();
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
  ///Retrieve a list of playgrounds by search terms
  ///return list of playgrounds
  ///
  List<Playground> search(String search) {

    _controller.getPlaygroundsBySearch(search).then((response) {

    }).catchError((error){
      print(error);
      //TODO
    });

    Playground playground1 = new Playground();
    playground1.name = "Playground A";
    playground1.address = "45 Avenue Alain Fenetti";
    playground1.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
    playground1.imgPath = "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.oxforddictionaries.com%2Fwp-content%2Fuploads%2Fmountain-names.jpg&f=1";
    playground1.latitude = 45.764546;
    playground1.longitude = 4.835689;
    playground1.sports.add(Sport.createNew(1,"Basketball"));
    playground1.sports.add(Sport.createNew(3,"Handball"));
    playground1.sports.add(Sport.createNew(4,"Volleyball"));

    Playground playground2 = new Playground();
    playground2.name = "Playground B";
    playground2.address = "25 Impasse des marroniers";
    playground2.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fi0.wp.com%2Ftechbeasts.com%2Fwp-content%2Fuploads%2F2016%2F12%2FNature-Mountain-HD-Wallpapers.jpg&f=1";
    playground2.latitude = 45.746779;
    playground2.longitude = 4.824949;

    Playground playground3 = new Playground();
    playground3.name = "Playground C";
    playground3.address = "25 Impasse des marroniers";
    playground3.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.Kn6serGrBlmmo6rgxGloGwHaEK%26pid%3D15.1&f=1";
    playground3.latitude = 45.752989;
    playground3.longitude = 4.842779;

    Playground playground4 = new Playground();
    playground4.name = "Playground R";
    playground4.address = "25 Impasse des marroniers";
    playground4.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.Kn6serGrBlmmo6rgxGloGwHaEK%26pid%3D15.1&f=1";
    playground4.latitude = 45.753989;
    playground4.longitude = 4.848779;

    List<Playground> playgrounds = new List<Playground>();
    playgrounds.add(playground1);
    playgrounds.add(playground2);
    playgrounds.add(playground3);
    playgrounds.add(playground4);

    return playgrounds;
  }

}