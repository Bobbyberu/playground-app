
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/User.dart';

class ReportComment {

  int id;
  Comment comment;
  User author;
  String description;

  ReportComment({this.id, this.comment, this.author, this.description});

  factory ReportComment.getDefault() {
    return new ReportComment(
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