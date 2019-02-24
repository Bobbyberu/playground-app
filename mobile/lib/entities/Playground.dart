
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

  factory Playground.fromJson(Map<String, dynamic> json) {
    return Playground(
        id: json['id'] as int,
        name: json['name'] as String,
        isPrivate: json['isPrivate'] as bool,
        city: json['city'] as String,
        address: json['address'] as String,
        latitude: (json['latitude'] as num).toDouble(),
        longitude: (json['longitude'] as num).toDouble(),
        averageMark: (json.containsKey('averageMark')) ? (json['averageMark'] as num).toDouble() : null,
        covered: json['covered'] as bool,
        imgPath: json['imgPath'] as String,
        description: json['description'] as String,
        sports: (json.containsKey('sports')) ? (json['sports'] as List)
            .map((e) => Sport.fromJson(e as Map<String, dynamic>))
            .toSet() : null,
        players: (json.containsKey('sports')) ? (json['players'] as List)
            .map((e) => User.fromJson(e as Map<String, dynamic>))
            .toSet() : null
    );
  }
  Map<String, dynamic> toJson() => _$PlaygroundToJson(this);

  static bool validate(Playground newPlayground) {
    return (newPlayground.name != null && newPlayground.name.isNotEmpty)
        && (newPlayground.address != null && newPlayground.address.isNotEmpty)
        && (newPlayground.sports.length > 0);
  }

}