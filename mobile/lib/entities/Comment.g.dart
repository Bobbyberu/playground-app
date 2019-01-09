// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'Comment.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Comment _$CommentFromJson(Map<String, dynamic> json) {
  return Comment(
      id: json['id'] as int,
      playground:
          Playground.fromJson(json['playground'] as Map<String, dynamic>),
      author: User.fromJson(json['author'] as Map<String, dynamic>),
      mark: (json['mark'] as num).toDouble(),
      comment: json['comment'] as String,
      archived: json['archived'] as bool);
}

Map<String, dynamic> _$CommentToJson(Comment instance) => <String, dynamic>{
      'id': instance.id,
      'playground': instance.playground,
      'author': instance.author,
      'mark': instance.mark,
      'comment': instance.comment,
      'archived': instance.archived
    };
