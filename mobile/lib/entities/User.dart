import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:json_annotation/json_annotation.dart';


///
/// User entity class
///
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

  Sport playing;
  Set<Playground> favouritePlaygrounds = new Set();

  User({this.id, this.username, this.mail, this.birthDate, this.password, this.city, this.enabled, this.archived, this.banned, this.playing, this.favouritePlaygrounds});

  factory User.getDefault() {
    return new User(
      id: 0,
      username: "",
      mail: "",
      birthDate: DateTime.now(),
      password: "",
      city: "",
      enabled: false,
      archived: false,
      banned: false,
      playing: null,
      favouritePlaygrounds: new Set(),
    );
  }

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
        id: json['id'] as int,
        username: json['username'] as String,
        mail: json['mail'] as String,
        birthDate: (json['birthDate'] != null) ?  DateTime.parse(json['birthDate'] as String) : null,
        password: json['password'] as String,
        city: json['city'] as String,
        enabled: json['enabled'] as bool,
        archived: json['archived'] as bool,
        banned: json['banned'] as bool,
        playing: (json['playing'] != null) ? Sport.fromJson(json['playing'] as Map<String, dynamic>) : null,
        favouritePlaygrounds: (json['favouritePlaygrounds'] == null) ? Set() : (json['favouritePlaygrounds'] as List)
            .map((e) => Playground.fromJson(e as Map<String, dynamic>))
            .toSet()
    );
  }

  Map<String, dynamic> toJson() => <String, dynamic>{
    'id': this.id,
    'username': this.username,
    'mail': this.mail,
    'birthDate': this.birthDate.toIso8601String(),
    'password': this.password,
    'city': this.city,
    'enabled': this.enabled,
    'archived': this.archived,
    'banned': this.banned,
    'playing' : (this.playing == null) ? null : this.playing.toJson(),
    'favouritePlaygrounds': (this.favouritePlaygrounds != null) ? this.favouritePlaygrounds.toList() : new List()
  };
}