import 'package:Playground/entities/Sport.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/sport/SportDisplay.dart';
import 'package:flutter/material.dart';

class UniqueSportSelection extends StatefulWidget {

  final Set<Sport> sports;

  const UniqueSportSelection({Key key, this.sports}) : super(key: key);

  @override
  State<StatefulWidget> createState() => UniqueSportSelectionState();

}

class UniqueSportSelectionState extends State<UniqueSportSelection> {

  Sport selectedSport = null;

  @override
  Widget build(BuildContext context) {
    List<ListTile> listSports = new List<ListTile>();
    for(Sport sport in widget.sports) {
      listSports.add(new ListTile(
        selected: (selectedSport != null) ? selectedSport.id == sport.id : false,
        title: new SportDisplay(sport: sport),
        onTap: () {
          setState(() {
            selectedSport = sport;
          });
        },
      ));
    }

    return new Material(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text("Quel sport pratiquez vous ?"),
          Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: listSports,
          ),
          new PlaygroundButton("Valider", () { Navigator.of(context).pop(selectedSport);})
        ],
      ),
    );
  }

}