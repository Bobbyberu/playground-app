import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:json_annotation/json_annotation.dart';

part 'User.g.dart';

/**
 * User entity class
 */
@JsonSerializable(nullable: false)
class User {

  int id;
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

  User({this.id, this.username, this.mail, this.birthDate, this.password, this.city, this.enabled, this.archived, this.banned, this.friends, this.favouriteSports, this.favouritePlaygrounds});

  factory User.getDefault() {
    return new User(
      id: 0,
      username: "Jean-Louis",
      mail: "",
      birthDate: DateTime.now(),
      password: "",
      city: "",
      enabled: false,
      archived: false,
      banned: false,
      favouritePlaygrounds: new Set(),
      favouriteSports: new Set(),
      friends: new Set()
    );
  }

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);

}