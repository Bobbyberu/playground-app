
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/User.dart';

class SignalComment {

  int id;
  Comment comment;
  User author;
  String description;

  SignalComment({this.id, this.comment, this.author, this.description});

  factory SignalComment.getDefault() {
    return new SignalComment(
      id: 0,
      description: "",
      comment: null,
      author: null
    );
  }

  Map<String, dynamic> toJson() {
    return <String, dynamic> {
      'id' : this.id,
      'description' : this.description,
      'author' : this.author.toJson(),
      'comment' : this.comment.toJson()
    };
  }

}