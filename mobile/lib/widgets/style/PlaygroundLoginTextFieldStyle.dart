
import 'package:flutter/material.dart';

class PlaygroundLoginTextFieldStyle {

  static TextStyle getStyle(BuildContext context) {
    return new TextStyle(
      color: Theme.of(context).primaryColorDark,
      fontSize: 18
    );
  }

  static InputDecoration getDecoration(BuildContext context, String hintText, IconData icon){
    return new InputDecoration(
      hintText: hintText,
      fillColor: Colors.white,
      filled: true,
      errorMaxLines: 2,
      prefixIcon: new Icon(icon),
      contentPadding: EdgeInsets.only(left: 12, right: 12, top: 16, bottom: 16),
      border: new OutlineInputBorder(
        borderRadius: BorderRadius.all(Radius.circular(5)),
        borderSide: BorderSide(color: Colors.white)
      ),
      focusedBorder: new OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.circular(5)),
          borderSide: BorderSide(color: Theme.of(context).primaryColor)
      ),
      errorBorder: new OutlineInputBorder(
        borderRadius: BorderRadius.all(Radius.circular(5)),
        borderSide: BorderSide(color: Colors.redAccent)
      )
    );
  }

}