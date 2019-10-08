
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/services/UserService.dart';
import 'package:flutter/material.dart';

class FavouritePlaygroundToggle extends StatefulWidget {

  final int playgroundId;

  const FavouritePlaygroundToggle({Key key, this.playgroundId}) : super(key: key);

  @override
  State<StatefulWidget> createState() => FavouritePlaygroundToggleState();

}

class FavouritePlaygroundToggleState extends State<FavouritePlaygroundToggle> {

  bool _isFavourite;
  bool _isLoading;
  UserService _userService = new UserService();

  @override
  void initState() {
    super.initState();
    _isLoading = true;
    checkIfFavourite();
  }

  void checkIfFavourite() async {
    _isLoading = true;
    await _userService.checkIfFavorite(widget.playgroundId).then((response) {
      setState(() {
        _isFavourite = response;
        _isLoading = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return new IconButton(
      iconSize: 30,
      icon: (_isLoading) ?
        new Icon(Icons.cached, color: Colors.grey, size: 12) :
        (_isFavourite) ?
          new Icon(
            Icons.favorite,
            color: Theme.of(context).primaryColor,
          ) :
          new Icon(
            Icons.favorite_border,
            color: Colors.grey,
          ),
      color: Colors.transparent,
      tooltip: "Ajouter aux favoris",
      onPressed: (_isLoading) ? null : () {
        setState(() {
          _isLoading = true;
        });
        _userService.togglePlaygroundFavorite(widget.playgroundId).then((response) {
          setState(() {
            _isFavourite = response;
            _isLoading = false;
          });
        });
      },
    );
  }

}