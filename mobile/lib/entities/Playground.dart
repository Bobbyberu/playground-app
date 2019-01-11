
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
  String name = "";
  bool isPrivate = false;
  String address = "";
  String city = "";
  double latitude;
  double longitude;
  double averageMark;
  bool covered = false;
  String imgPath;
  String description;

  Set<Sport> sports = new Set<Sport>();
  Set<User> players = new Set<User>();

  Playground({
    this.id,
    this.name,
    this.isPrivate,
    this.city,
    this.address,
    this.latitude,
    this.longitude,
    this.averageMark,
    this.covered,
    this.imgPath,
    this.description,
    this.sports,
    this.players
  });

  factory Playground.getDefault(){
    return new Playground(
      id: 0,
      name: "",
      isPrivate: false,
      address: "",
      city: "",
      latitude: 0,
      longitude: 0,
      averageMark: 0,
      covered: false,
      description: "",
      sports: new Set<Sport>(),
      players: new Set<User>()
    );
  }

  factory Playground.fromJson(Map<String, dynamic> json) => _$PlaygroundFromJson(json);
  Map<String, dynamic> toJson() => _$PlaygroundToJson(this);

}