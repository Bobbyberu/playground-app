import 'package:flutter/material.dart';

class PlaygroundButton extends StatelessWidget {

  final String _text;
  final VoidCallback _onTap;

  PlaygroundButton(this._text,this._onTap);

  @override
  Widget build(BuildContext context) {
    return new MaterialButton(
      child: new Text(
        this._text,
        style: new TextStyle(
          fontSize: 28,
        ),
      ),
      textColor: Colors.white,
      color: Theme.of(context).primaryColor,
      onPressed: _onTap,
      padding: EdgeInsets.only(left: 16, right: 16, bottom: 12, top: 12),
    );
  }

}