
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/User.dart';
import 'package:Playground/services/UserService.dart';
import 'package:flutter/material.dart';

///
/// Widget to display user avatar in circle width border style
///
class UserAvatarCircle extends StatefulWidget {

  final double size;
  final User user;

  const UserAvatarCircle({Key key, this.size, this.user}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new UserAvatarCircleState();

}

class UserAvatarCircleState extends State<UserAvatarCircle> {

  String url;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    url = UserService.getUserAvatarUrl(widget.user);

    return new Container(
      decoration: new BoxDecoration(
          shape: BoxShape.circle,
          border: Border.all(width: 4, color: Theme.of(context).primaryColor),
          image: DecorationImage(
            fit: BoxFit.cover,
            image: new NetworkImage(
                url,
                headers: {
                  "Accept": "image/png",
                  "Accept-Encoding": "*"
                }
            ),

          )
      ),
      width: widget.size,
      height: widget.size,
    );
  }

}