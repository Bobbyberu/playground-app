import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:json_annotation/json_annotation.dart';

part 'User.g.dart';

/**
 * User entity class
 */
@JsonSerializable(nullable: false)
class User {

  String username;
  String mail;
  DateTime birthDate;
  String password;
  String city;
  bool enabled;
  bool archived;
  bool banned;

  Set<User> friends = new Set();
  Set<Sport> favouriteSports = new Set();
  Set<Playground> favouritePlaygrounds = new Set();

  User({this.username, this.mail, this.birthDate, this.password, this.city, this.enabled, this.archived, this.banned, this.friends, this.favouriteSports, this.favouritePlaygrounds});

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);

}