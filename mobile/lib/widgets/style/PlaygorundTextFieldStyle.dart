import 'package:flutter/material.dart';

class PlaygroundTextFieldStyle {

  static InputDecoration getDecoration(BuildContext context, String hintText) {
    return new InputDecoration(
         border: OutlineInputBorder(
            borderSide: BorderSide(
                color: Theme.of(context).primaryColor,
                width: 1
            )
        ),
        contentPadding: EdgeInsets.only(left: 24,top: 10,bottom: 10),
        hintText: hintText,
        hintStyle: TextStyle(
            color: Theme.of(context).primaryColorLight,
            fontStyle: FontStyle.italic
        )
    );
  }

  static TextStyle getStyle(BuildContext context) {
    return new TextStyle(
        fontSize: 16,
        color: Theme.of(context).primaryColorDark
    );
  }

}