import 'package:Playground/entities/Sport.dart';
import 'package:Playground/services/SportService.dart';
import 'package:flutter/material.dart';

class PlaygroundSportDropDownList extends StatefulWidget {

  List<Sport> availableSports = new List<Sport>();
  FormFieldValidator<Sport> onChanged = (sport) {};
  FormFieldValidator<Sport> onSaved = (sport) {};

  PlaygroundSportDropDownList({this.availableSports,this.onChanged,this.onSaved});

  @override
  State<StatefulWidget> createState() => new PlaygroundSportDropDownListState();
}

class PlaygroundSportDropDownListState extends State<PlaygroundSportDropDownList> {

  SportService _sportService = new SportService();

  @override
  Widget build(BuildContext context) {
    return new DropdownButtonFormField(
      items: widget.availableSports.map((sport) => new DropdownMenuItem(value: sport, child: Text(sport.name))).toList(),
      decoration: InputDecoration(),
      onChanged: widget.onChanged,
      onSaved: widget.onSaved,
    );
  }

}