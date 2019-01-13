
import 'package:flutter/material.dart';

class PlaygroundMarkSelector extends FormField<int> {

  PlaygroundMarkSelector({
    FormFieldSetter<int> onSaved,
    FormFieldValidator<int> validator,
    int initialValue = 3,
    bool autovalidate = false
  }) : super(
    onSaved: onSaved,
    validator: validator,
    initialValue: initialValue,
    autovalidate: autovalidate,
    builder: (FormFieldState<int> state) {

      List<Widget> stars = new List();
      for(int i = 1 ; i <= 5 ; i++) {
        stars.add(
            new IconButton(
                icon: (i <= state.value) ? new Icon(Icons.star, size: 30) : new Icon(Icons.star_border, size: 30),
                color: Colors.green,
                onPressed: () {
                  state.didChange(i);
                })
        );
      }

      return Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: stars,
      );
    }
  );

}