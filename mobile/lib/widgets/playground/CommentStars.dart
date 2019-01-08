
import 'package:flutter/material.dart';

class CommentsStars extends StatelessWidget {

  final double markMax;
  final double mark;
  final Color color;

  const CommentsStars({Key key, this.markMax, this.mark, this.color}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Widget> stars = new List<Widget>();

    var starFull = new Icon(Icons.star, color: color);
    var starHalf = new Icon(Icons.star_half, color: color);
    var starEmpty = new Icon(Icons.star_border, color: color);

    var full = mark.floor();
    var half = mark - mark.floor();
    var empty = (markMax - mark).floor();


    for (int i = 0 ; i < full ; i++) {
      stars.add(starFull);
    }

    (half > 0.4) ?
      (half > 0.75) ?
        stars.add(starFull)
        : stars.add(starHalf)
      : stars.add(starEmpty);

    for (int i = 0 ; i < empty ; i++) {
      stars.add(starEmpty);
    }

    return new Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: stars
    );
  }

}