import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';

class Comment {

  int id;
  User user;
  Playground playground;
  double mark;
  String comment;
  bool archived;

  Comment() {
    this.id = 0;
    this.user = null;
    this.playground = null;
    this.mark = 0;
    this.comment = "";
    this.archived = false;
  }

}