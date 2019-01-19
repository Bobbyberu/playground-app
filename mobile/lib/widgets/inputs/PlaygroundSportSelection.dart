import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/services/SportService.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/sport/SportDisplay.dart';
import 'package:Playground/widgets/text/PlaygroundFormLabel.dart';
import 'package:flutter/material.dart';

class PlaygroundSportSelection extends StatefulWidget {

  Playground playground;

  PlaygroundSportSelection(playground) : super(){
    this.playground = playground;
  }

  @override
  State<StatefulWidget> createState() => new PlaygroundSportSelectionState();
}

class PlaygroundSportSelectionState extends State<PlaygroundSportSelection> {
  Set<Sport> availableSports;
  Set<Sport> selectedSports;

  void loadAllSport() async {
    SportService sportService = new SportService();
    await sportService.getSports().then((response) {
      setState(() {
        this.availableSports = response;
      });
    });
  }

  @override
  void initState(){
    this.availableSports = new Set<Sport>();
    loadAllSport();
    this.selectedSports = new Set();
    this.selectedSports.addAll(widget.playground.sports);
    super.initState();
  }


  @override
  Widget build(BuildContext context) {

    List<ListTile> listSports = new List<ListTile>();
    for(Sport sport in availableSports) {
      listSports.add(new ListTile(
        selected: selectedSports.contains(sport),
        title: new SportDisplay(sport: sport),
        onTap: () {
          setState(() {
            (selectedSports.contains(sport)) ? selectedSports.remove(sport) : selectedSports.add(sport);
          });
        },
      ));
    }

    return new Scaffold(
      appBar: new AppBar(),
      body: new Material(

        child: new SingleChildScrollView(

          child: new ConstrainedBox(
            constraints: new BoxConstraints(),
            child: new Padding(
              padding: EdgeInsets.all(8),
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                   new Padding(
                     padding: EdgeInsets.all(8),
                     child: new PlaygroundFormLabel("Selectionner des sports")
                   ),

                    new Column(
                      children: listSports,
                    ),

                  new PlaygroundButton(
                    "Valider",
                    () {
                      Navigator.of(context).pop(selectedSports);
                    }
                  )
                ],
              ),
            ),
          )

       )

      )
    );
  }

}