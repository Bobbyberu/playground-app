
import 'dart:io';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';

class PlaygroundService {

  Future<bool> save(Playground playground, File img) async{
    // TODO call ws to save the playground object
    return true;
  }

  List<Playground> getPlaygroundsNearMe() {

    Playground playground1 = new Playground();
    playground1.name = "Playground 1";
    playground1.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
    playground1.imgPath = "https://proxy.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.oxforddictionaries.com%2Fwp-content%2Fuploads%2Fmountain-names.jpg&f=1";
    playground1.latitude = 45.764045;
    playground1.longitude = 4.835675;
    playground1.sports.add(new Sport(1,"Basketball"));
    playground1.sports.add(new Sport(3,"Handball"));
    playground1.sports.add(new Sport(4,"Volleyball"));

    Playground playground2 = new Playground();
    playground2.name = "Playground 2";
    playground2.imgPath = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fi0.wp.com%2Ftechbeasts.com%2Fwp-content%2Fuploads%2F2016%2F12%2FNature-Mountain-HD-Wallpapers.jpg&f=1";
    playground2.latitude = 45.746778;
    playground2.longitude = 4.824748;

    Playground playground3 = new Playground();
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

}