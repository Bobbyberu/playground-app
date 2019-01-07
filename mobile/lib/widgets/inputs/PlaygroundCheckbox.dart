import 'package:flutter/material.dart';

class PlaygroundCheckbox extends StatefulWidget {

  bool value = false;
  bool dark = false;
  FormFieldValidator<bool> onChanged = (value) {};

  PlaygroundCheckbox({this.value,this.dark,this.onChanged});

  @override
  State<StatefulWidget> createState() => new PlaygroundCheckboxState();

}

class PlaygroundCheckboxState extends State<PlaygroundCheckbox> {

  @override
  Widget build(BuildContext context) {
    return Checkbox(
      value: widget.value,
      activeColor: (widget.dark != null && widget.dark) ? Theme.of(context).primaryColorDark : Theme.of(context).primaryColor,
      onChanged: widget.onChanged,
      tristate: true,
    );
  }

}