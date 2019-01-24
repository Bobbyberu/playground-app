
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/pages/SignalCommentPage.dart';
import 'package:Playground/widgets/playground/CommentStars.dart';
import 'package:Playground/widgets/user/UserAvatarCircle.dart';
import 'package:Playground/widgets/user/UserSummary.dart';
import 'package:flutter/material.dart';

class CommentCard extends StatelessWidget {

  final Comment comment;

  const CommentCard({Key key, this.comment}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new Card(
      child : new Padding(
        padding: EdgeInsets.all(6),
        child: new Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[

            new Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                UserSummary(user: comment.author, avatarSize: 50),

                new Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    new CommentsStars(color: Colors.grey[700], mark: comment.mark, markMax: 5)
                  ],
                )
              ],
            ),

            new Padding(
              padding: EdgeInsets.only(top: 12, left: 4, right: 4),
              child: new Text(
                comment.comment,
                textAlign: TextAlign.justify,
              )
            ),


            new Row(
              mainAxisAlignment: MainAxisAlignment.end,
              mainAxisSize: MainAxisSize.max,
              children: <Widget>[
                new IconButton(icon: new Icon(Icons.flag, color: Colors.grey[700]), onPressed: () {
                  Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new SignalCommentPage(comment: comment)));
                })
              ],
            )

          ],
        ),
      )

    );
  }


}