
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/entities/User.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Playground.g.dart';

///
///Playground entity class
///Playground is the main object of the application. It contains informations about its location, comments, sports available,...
///
@JsonSerializable(nullable: false)
class Playground {

  int id;
  String name;
  bool isPrivate;
  String address;
  double latitude;
  double longitude;
  double averageMark;
  bool covered;
  String imgPath;
  String description;

  Set<Sport> sports;
  Set<User> listPlayers;

  Playground({
    this.id,
    this.name,
    this.isPrivate,
    this.address,
    this.latitude,
    this.longitude,
    this.averageMark,
    this.covered,
    this.imgPath,
    this.description,
    this.sports,
    this.listPlayers
  }) {
    this.address = "";
    this.description = "";
    this.isPrivate = false;
    this.covered = false;
    this.sports = new Set<Sport>();
    this.listPlayers = new Set<User>();
  }

  factory Playground.fromJson(Map<String, dynamic> json) => _$PlaygroundFromJson(json);
  Map<String, dynamic> toJson() => _$PlaygroundToJson(this);

}