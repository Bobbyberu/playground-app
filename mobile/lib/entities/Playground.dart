
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/entities/User.dart';

class Playground {

  int id;
  String name;
  bool isPrivate;
  String adresse;
  double latitude;
  double longitude;
  Set<User> listPlayers;
  double averageMark;
  Set<Sport> sports;
  bool isCovered;
  String imgPath;
  String description;

  Playground(){
    this.id = 0;
    this.name = "";
    this.adresse = "";
    this.isPrivate = false;
    this.latitude = 0;
    this.longitude = 0;
    this.averageMark = 0;
    this.isCovered = false;
    this.imgPath = "";
    this.description = "";

    this.listPlayers = Set<User>();
    this.sports = new Set<Sport>();
  }

  Set<Sport> get psports => sports;
  set (Set<Sport> sports) => this.sports = sports;
}