import 'package:flutter/material.dart';

class PlaygroundTextField extends StatelessWidget{

  final String hintText;
  final bool obscureText;

  PlaygroundTextField(this.hintText, this.obscureText);

  @override
  Widget build(BuildContext context) {
    return new TextField(
        cursorColor: Colors.blueGrey,
        decoration: InputDecoration(
            hintText: hintText,
            fillColor: Colors.white,
            contentPadding: EdgeInsets.only(top: 14,bottom: 14, left: 18, right: 18),
            filled: true,
            border: OutlineInputBorder(borderRadius: BorderRadius.circular(40), borderSide: BorderSide(color:Colors.white))
        )
    );
  }

}


class PlaygroundTextFormField extends StatelessWidget{

  String hintText = "";
  String value = "";
  bool obscureText = false;
  FormFieldValidator<String> validator = (value) {};
  FormFieldValidator<String> onSaved = (value) {};

  PlaygroundTextFormField({this.hintText, this.value, this.obscureText, this.validator, this.onSaved});

  @override
  Widget build(BuildContext context) {

    return new TextFormField( // Input
        obscureText: this.obscureText,
        initialValue: this.value,
        validator: this.validator,
        onSaved: this.onSaved,
        decoration: new InputDecoration(
          border: OutlineInputBorder(
              borderSide: BorderSide(
                  color: Colors.green,
                  width: 1
              )
          ),
          contentPadding: EdgeInsets.only(left: 24,top: 10,bottom: 10),
          hintText: this.hintText,
          hintStyle: TextStyle(
            color: Colors.grey,
            fontStyle: FontStyle.italic
          )

        ),
      style: TextStyle(
          fontSize: 22,
          color: Colors.black87
      ),

    );
  }

}