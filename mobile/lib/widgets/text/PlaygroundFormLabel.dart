import 'package:flutter/material.dart';

class PlaygroundFormLabel extends StatelessWidget {

  String text;

  PlaygroundFormLabel(this.text);

  @override
  Widget build(BuildContext context) {
    return new Text(
      this.text,
      style: new TextStyle(
          fontSize: 16
      )
    );
  }

}