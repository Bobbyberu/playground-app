// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'User.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) {
  return User(
      id: json['id'] as int,
      username: json['username'] as String,
      mail: json['mail'] as String,
      birthDate: DateTime.parse(json['birthDate'] as String),
      password: json['password'] as String,
      city: json['city'] as String,
      enabled: json['enabled'] as bool,
      archived: json['archived'] as bool,
      banned: json['banned'] as bool,
      friends: (json['friends'] as List)
          .map((e) => User.fromJson(e as Map<String, dynamic>))
          .toSet(),
      favouriteSports: (json['favouriteSports'] as List)
          .map((e) => Sport.fromJson(e as Map<String, dynamic>))
          .toSet(),
      favouritePlaygrounds: (json['favouritePlaygrounds'] as List)
          .map((e) => Playground.fromJson(e as Map<String, dynamic>))
          .toSet());
}

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'id': instance.id,
      'username': instance.username,
      'mail': instance.mail,
      'birthDate': instance.birthDate.toIso8601String(),
      'password': instance.password,
      'city': instance.city,
      'enabled': instance.enabled,
      'archived': instance.archived,
      'banned': instance.banned,
      'friends': instance.friends.toList(),
      'favouriteSports': instance.favouriteSports.toList(),
      'favouritePlaygrounds': instance.favouritePlaygrounds.toList()
    };
