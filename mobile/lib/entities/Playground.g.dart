// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Playground.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Playground _$PlaygroundFromJson(Map<String, dynamic> json) {
  return Playground(
      id: json['id'] as int,
      name: json['name'] as String,
      isPrivate: json['isPrivate'] as bool,
      city: json['city'] as String,
      address: json['address'] as String,
      latitude: (json['latitude'] as num).toDouble(),
      longitude: (json['longitude'] as num).toDouble(),
      averageMark: (json['averageMark'] as num).toDouble(),
      covered: json['covered'] as bool,
      imgPath: json['imgPath'] as String,
      description: json['description'] as String,
      sports: (json['sports'] as List)
          .map((e) => Sport.fromJson(e as Map<String, dynamic>))
          .toSet(),
      players: (json['players'] as List)
          .map((e) => User.fromJson(e as Map<String, dynamic>))
          .toSet());
}

Map<String, dynamic> _$PlaygroundToJson(Playground instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'isPrivate': instance.isPrivate,
      'address': instance.address,
      'city': instance.city,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
      'averageMark': instance.averageMark,
      'covered': instance.covered,
      'imgPath': instance.imgPath,
      'description': instance.description,
      'sports': instance.sports.toList(),
      'players': instance.players.toList()
    };
