
import 'dart:convert';

import 'package:Playground/controllers/CommentController.dart';
import 'package:Playground/controllers/PlaygroundController.dart';
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/User.dart';

class CommentService {

  CommentController _controller = new CommentController();
  PlaygroundController _playgroundController = new PlaygroundController();

  ///
  /// save a comment to the database
  /// return bool success
  ///
  Future<bool> save(Comment comment) async {
    bool res = false;

    await _controller.postComment(comment).then((response) {
      res = response.statusCode != null && (response.statusCode == 201 || response.statusCode == 200);
    }).catchError((error) {
      _controller.printError(error);
      res = false;
    });

    return res;
  }

  ///
  /// Retrieve the comments of a specified playground
  /// return List of Comment
  ///
  Future<List<Comment>> getCommentsOfPlayground(int idPlayground) async{
    List<Comment> comments = new List();

    await _playgroundController.getCommentsOfPlayrgound(idPlayground).then((response) {
      _playgroundController.printResponse(response);
      if(response.statusCode != null && response.statusCode == 200) {
        List<dynamic> commentJson = json.decode(response.body);

        commentJson.forEach((c) {
          Comment comment = Comment.fromJson(c);
          comments.add(comment);
        });
      }
    });

    /*
    Comment comment1 = new Comment();
    comment1.comment = "C'est génial !";
    comment1.mark = 4;
    comment1.author = User.getDefault();
    comments.add(comment1);

    Comment comment2 = new Comment();
    comment2.comment = "Ouffissime ! Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
    comment2.mark = 4;
    comment2.author = User.getDefault();
    comments.add(comment2);

    Comment comment3 = new Comment();
    comment3.comment = "ça va, ça passe";
    comment3.mark = 3;
    comment3.author = User.getDefault();
    comments.add(comment3);

    Comment comment4 = new Comment();
    comment4.comment = "YEEEEEEEEEEEEES";
    comment4.mark = 4.2;
    comment4.author = User.getDefault();
    comments.add(comment4);*/

    return comments;
  }

  ///
  /// Calculate average mark of all the comments of a playground
  /// return double average
  ///
  double getAverageOfComment(List<Comment> comments) {
    if (comments.isEmpty) return 0;
    double sum = 0;
    comments.forEach((c) { sum += c.mark; });
    return (sum / comments.length);
  }

}