import 'package:flutter/material.dart';

class PlaygroundCheckbox extends StatefulWidget {

  bool value = false;
  FormFieldValidator<bool> onChanged = (value) {};

  PlaygroundCheckbox({this.value,this.onChanged});

  @override
  State<StatefulWidget> createState() => new PlaygroundCheckboxState();

}

class PlaygroundCheckboxState extends State<PlaygroundCheckbox> {

  @override
  Widget build(BuildContext context) {
    return Checkbox(
      value: widget.value,
      activeColor: Theme.of(context).primaryColor,
      onChanged: widget.onChanged,
      tristate: true,
    );
  }

}