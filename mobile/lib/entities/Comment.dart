import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:json_annotation/json_annotation.dart';

///
/// Comment entity class
/// Comment represents a comment made by a user on a playground with a mark
///
@JsonSerializable(nullable: false)
class Comment {

  int id;
  Playground playground;
  User author;
  double mark;
  String comment;
  bool archived;

  Comment({this.id, this.playground, this.author, this.mark, this.comment, this.archived});

  factory Comment.fromJson(Map<String, dynamic> json) {
    return Comment(
      id: json['id'] as int,
      mark: json['mark'] as double,
      comment: json['comment'],
      archived: json['archived'] as bool,
      author: (json.containsKey('author')) ? User.fromJson(json['author']) : null,
      playground: (json.containsKey('playground')) ? Playground.fromJson(json['playground']) : null
    );
  }

  Map<String, dynamic> toJson() {
    return <String, dynamic> {
      'id' : this.id,
      'mark': this.mark,
      'comment': (this.comment == null) ? null : this.comment,
      'archived' : this.archived,
      'author': (this.author == null) ? null : this.author.toJson(),
      'playground': (this.playground == null) ? null : this.playground.toJson()
    };
  }

  factory Comment.getDefault() {
    return new Comment(
      id: 0,
      playground: null,
      author: null,
      comment: "",
      mark: 0,
      archived: false
    );
  }

}