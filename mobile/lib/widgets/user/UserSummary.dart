
import 'package:Playground/entities/User.dart';
import 'package:Playground/services/UserService.dart';
import 'package:Playground/widgets/user/UserAvatarCircle.dart';
import 'package:flutter/material.dart';

class UserSummary extends StatefulWidget {

  final User user;
  final double avatarSize;
  final bool playing;

  const UserSummary({Key key, this.user, this.avatarSize, this.playing}) : super(key: key);

  @override
  State<StatefulWidget> createState() => UserSummaryState();


}


class UserSummaryState extends State<UserSummary> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: <Widget>[
        new UserAvatarCircle(user: widget.user, size: widget.avatarSize),
        new Padding(
            padding: EdgeInsets.only(left: 8),
            child: new Text(widget.user.username, style: new TextStyle(fontWeight: FontWeight.bold))
        ),
        (widget.playing != null && widget.playing) ? Padding(padding: EdgeInsets.only(left: 12), child: new Text(widget.user.playing.symbol, style: TextStyle(fontSize: 22))) : Container()
      ],
    );
  }

}