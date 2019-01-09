import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:json_annotation/json_annotation.dart';

part 'Comment.g.dart';

/**
 * Comment entity class
 * Comment represents a comment made by a user on a playground with a mark
 */
@JsonSerializable(nullable: false)
class Comment {

  int id;
  Playground playground;
  User author;
  double mark;
  String comment;
  bool archived;

  Comment({this.id, this.playground, this.author, this.mark, this.comment, this.archived});

  factory Comment.fromJson(Map<String, dynamic> json) => _$CommentFromJson(json);
  Map<String, dynamic> toJson() => _$CommentToJson(this);

}