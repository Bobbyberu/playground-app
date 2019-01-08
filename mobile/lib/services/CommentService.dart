
import 'package:Playground/entities/Comment.dart';

class CommentService {

  /**
   * Retrieve the comments of a specified playground
   * return List of Comment
   */
  List<Comment> getCommentsOfPlayground(int idPlayground) {
    List<Comment> comments = new List();

    Comment comment1 = new Comment();
    comment1.comment = "C'est génial !";
    comment1.mark = 4;
    comments.add(comment1);

    Comment comment2 = new Comment();
    comment2.comment = "Ouffissime !";
    comment2.mark = 5;
    comments.add(comment2);

    Comment comment3 = new Comment();
    comment3.comment = "ça va, ça passe";
    comment3.mark = 2.5;
    comments.add(comment3);

    Comment comment4 = new Comment();
    comment4.comment = "YEEEEEEEEEEEEES";
    comment4.mark = 4.2;
    comments.add(comment4);

    return comments;
  }

  /**
   * Calculate average mark of all the comments of a playground
   * return double average
   */
  double getAverageOfComment(List<Comment> comments) {
    double sum = 0;
    comments.forEach((c) { sum += c.mark; });
    return (sum / comments.length);
  }

}