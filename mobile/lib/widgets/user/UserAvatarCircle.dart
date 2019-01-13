
import 'package:Playground/entities/User.dart';
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

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Container(
      decoration: new BoxDecoration(
          shape: BoxShape.circle,
          border: Border.all(width: 4, color: Theme.of(context).primaryColor),
          image: DecorationImage(
            fit: BoxFit.cover,
            image: new NetworkImage(
              "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fyt3.ggpht.com%2F-Zbx5nbYMCM8%2FAAAAAAAAAAI%2FAAAAAAAAAAA%2FYZ4Lebmnjz0%2Fs900-c-k-no-rj-c0xffffff%2Fphoto.jpg&f=1",
            ),
          )
      ),
      width: widget.size,
      height: widget.size,
    );
  }

}