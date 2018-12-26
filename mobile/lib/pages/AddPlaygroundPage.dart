import 'dart:io';

import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/inputs/PlaygroundCheckbox.dart';
import 'package:Playground/widgets/inputs/PlaygroundSportSelection.dart';
import 'package:Playground/widgets/inputs/PlaygroundTextField.dart';
import 'package:Playground/widgets/text/PlaygroundFormLabel.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class AddPlaygroundPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new AddPlaygroundPageState();
}

class AddPlaygroundPageState extends State<AddPlaygroundPage> {

  @override
  Widget build(BuildContext context) {
    return  new AddPlaygroundPageContainer();
  }

}

class AddPlaygroundPageContainer extends StatefulWidget {
  
  @override
  State<StatefulWidget> createState() => new AddPlaygroundPageContainerState();

}

class AddPlaygroundPageContainerState extends State<AddPlaygroundPageContainer> {

  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  final Playground newPlayground = new Playground();
  File playgroundImg = null;

  Future _openDialogAddItemSelection(BuildContext context) async {

    Set<Sport> sports = await Navigator.of(context).push(
      new MaterialPageRoute<Set<Sport>>(
          builder: (BuildContext context) {
            return new PlaygroundSportSelection(newPlayground);
          },
          fullscreenDialog: true)
    );

    setState(() {
      if(sports != null) {
        newPlayground.sports.clear();
        newPlayground.sports = sports;
      }
    });
  }

  @override
  Widget build(BuildContext context) {

    List<Widget> playgroundSports = new List<Widget>();
    for(Sport sport in newPlayground.sports) {
      playgroundSports.add(
        new Row(
          children : <Widget>[
            new IconButton(icon: new Icon(Icons.remove), onPressed: (){
              setState(() {
                newPlayground.sports.remove(sport);
              });
            }),
            new Text(sport.name),
          ]
        )
      );
    }

    return new Scaffold(
        appBar: AppBar(
              backgroundColor: Colors.green,
              title: new Text("Nouveau Playground"),
        ),

          body: new SingleChildScrollView(
            child: new ConstrainedBox(
            constraints: new BoxConstraints(),
              child: new Form(
                key: _formKey,
                child: new Padding(
                    padding: EdgeInsets.all(18),
                    child: new Column(
                      children: [
                        new Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new PlaygroundFormLabel("Nom")
                              ),

                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new PlaygroundTextFormField(
                                      value: newPlayground.name,
                                      hintText: "City stade",
                                      obscureText: false,
                                      validator : (value) {
                                        if(value.isEmpty) return "Le champ Nom est obligatoire";
                                      },
                                      onSaved: (value) {
                                        newPlayground.name = value.trim();
                                      }
                                  )
                              ),

                              new Padding(
                                padding: EdgeInsets.all(8),
                                child: new PlaygroundFormLabel("Adresse"),
                              ),

                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new PlaygroundTextFormField(
                                      hintText :"24 Rue du Plaisir...",
                                      obscureText: false,
                                      validator: (value) {
                                        if(value.isEmpty) return "Le champ Adresse est obligatoire";
                                      },
                                      onSaved: (value) {
                                        // TODO
                                      }
                                  )
                              ),

                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new Row(
                                      children: [
                                        new PlaygroundFormLabel("Sports"),
                                        new IconButton(icon: new Icon(Icons.add), onPressed: () {
                                          _openDialogAddItemSelection(context);
                                        })
                                      ]
                                  )
                              ),

                              new Padding(
                                padding: EdgeInsets.all(8),
                                child: new Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: playgroundSports,
                                ),
                              ),

                              new Padding(
                                padding: EdgeInsets.all(8),
                                child: new PlaygroundFormLabel("Description"),
                              ),

                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new PlaygroundTextFormField(
                                      value: newPlayground.description,
                                      hintText :"2 paniers de basket, fermé jours fériés, ...",
                                      obscureText: false,
                                      onSaved: (value) {
                                        newPlayground.description = value;
                                      }
                                  )
                              ),

                              new Padding(
                                  padding: EdgeInsets.all(8),
                                  child: new Row(
                                    children: <Widget>[
                                      new PlaygroundFormLabel("Photo"),
                                      new IconButton(
                                        icon: new Icon(Icons.camera_alt),
                                        onPressed: () async {
                                          playgroundImg = await ImagePicker.pickImage(source: ImageSource.gallery);
                                          setState(() {

                                          });
                                        }
                                      ),
                                      (playgroundImg != null) ? new Image.file(playgroundImg, height:50.0): new Text("Sélectionner une image")
                                    ]
                                  )
                              ),

                              new Padding(
                                padding: EdgeInsets.all(8),
                                child: new Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: <Widget>[
                                    new Row(
                                      children: <Widget>[
                                        new PlaygroundCheckbox(
                                            value: newPlayground.isPrivate,
                                            onChanged: (value) {
                                              if (value == null) value = false;
                                              setState(() {
                                                newPlayground.isPrivate = value;
                                              });
                                            }
                                        ),
                                        new PlaygroundFormLabel("Privé")
                                      ],
                                    ),
                                    new Row(
                                      children: <Widget>[
                                        new PlaygroundCheckbox(
                                            value: newPlayground.isCovered,
                                            onChanged: (value) {
                                              if (value == null) value = false;
                                              setState(() {
                                                newPlayground.isCovered = value;
                                              });
                                            }
                                        ),
                                        new PlaygroundFormLabel("Couvert")
                                      ],
                                    )
                                  ],
                                ),
                              ),
                            ]
                          ),

                          new Padding(
                              padding: EdgeInsets.all(8),
                              child: new PlaygroundButton(
                                  "Valider",
                                  () => setState(() async {
                                    if(_formKey.currentState.validate()){
                                      PlaygroundService playgroundService = new PlaygroundService();
                                      bool result = await playgroundService.save(newPlayground, playgroundImg);
                                      if (result) {
                                        showDialog(
                                          context: context,
                                          builder: (BuildContext context) {
                                            return new AlertDialog(
                                              title: new Text("Validation"),
                                              content: new Text("Le nouveau playground a été ajouté !"),
                                              actions: <Widget>[
                                                new FlatButton(onPressed: () {}, child: new Text("Ok"))
                                              ],
                                            );
                                          }
                                        );
                                      }
                                    }
                                  })
                              )
                          )

                        ]
                    )
                )
            ),
          )
        ),
    );
  }

}
