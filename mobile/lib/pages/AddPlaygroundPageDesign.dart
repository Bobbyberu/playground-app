import 'dart:io';

import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/inputs/PlaygroundCheckbox.dart';
import 'package:Playground/widgets/inputs/PlaygroundSportSelection.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';


/**
 * Page for submitting a new playground to the database
 * User enter informations about a playground (locations, available sports, picture, ...) and submit it to the server
 */
class AddPlaygroundPageDesign extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new AddPlaygroundPageDesignState();
}

class AddPlaygroundPageDesignState extends State<AddPlaygroundPageDesign> {

  @override
  Widget build(BuildContext context) {
    return new AddPlaygroundPageContainer();
  }

}

class AddPlaygroundPageContainer extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new AddPlaygroundPageContainerState();

}

class AddPlaygroundPageContainerState extends State<AddPlaygroundPageContainer> {

  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  Playground newPlayground = Playground.getDefault();
  File playgroundImg = null;


  ///
  ///Navigate to a new component to select and return a list of sports
  ///
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

  ///
  /// Validate form and call ws to add the new playground
  ///
  Future validateForm() async {
    if(_formKey.currentState.validate()){
      _formKey.currentState.save();
      PlaygroundService playgroundService = new PlaygroundService();
      await playgroundService.save(newPlayground, playgroundImg).then((result) {
        if (result) {
          showDialog(
              context: context,
              builder: (BuildContext context) {
                return new AlertDialog(
                  title: new Text("Validation"),
                  content: new Text("Le nouveau playground a été ajouté !"),
                  actions: <Widget>[
                    new FlatButton(
                        onPressed: () {
                          Navigator.pushReplacementNamed(context, '/home');
                        },
                        child: new Text("Ok")
                    )
                  ],
                );
              }
          );
        } else {
          showDialog(
              context: context,
              builder: (BuildContext context) {
                return new AlertDialog(
                  title: new Text("Oh oh..."),
                  content: new Text("Un problème est venu lors de la validation. Veuillez réessayer plus tard."),
                  actions: <Widget>[
                    new FlatButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        child: new Text("Ok")
                    )
                  ],
                );
              }
          );
        }
      });
    }
  }


  @override
  Widget build(BuildContext context) {

    List<Widget> playgroundSportsLeft = new List<Widget>();
    List<Widget> playgroundSportsRight = new List<Widget>();
    bool left = true;

    for(Sport sport in newPlayground.sports) {
      Row sportRow = new Row(
          children : <Widget>[
            new IconButton(
                icon: new Icon(Icons.highlight_off, color: Colors.red),
                onPressed: () {
                  setState(() {
                    newPlayground.sports.remove(sport);
                  });
                }
            ),

            new Text(sport.name),
          ]
      );

      (left) ?
        playgroundSportsLeft.add(
            new Padding(
              padding: EdgeInsets.only(bottom: 6),
              child: sportRow
            )
        ) :
        playgroundSportsRight.add(
            new Padding(
                padding: EdgeInsets.only(bottom: 6),
                child: sportRow
            )
        );
      left = !left;
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
              child: new Column(
                children: <Widget>[

                  // Photo
                  new Container(
                    constraints: new BoxConstraints.expand(
                      height: 160
                    ),
                    child: new Stack(
                      fit: StackFit.passthrough,
                      children: <Widget>[
                        (playgroundImg == null) ?
                          new Container(
                            height: 160,
                            color: Theme.of(context).primaryColorDark,
                            child: Image.asset(
                              'images/default_playground.png',
                              fit: BoxFit.cover,
                              height: 160.0,
                            )
                          ) :
                          new Container(
                            height: 160,
                            color: Theme.of(context).primaryColorDark,
                            child: Image.file(
                              playgroundImg,
                              fit: BoxFit.cover,
                              height: 160.0,
                            )
                          ),
                        new Positioned(
                            bottom: 4,
                            right: 4,
                            child: new Padding(
                                padding: EdgeInsets.all(8),
                                child: new Container(
                                    decoration: BoxDecoration(
                                      borderRadius: BorderRadius.circular(50),
                                      color: Theme.of(context).primaryColor,
                                    ),
                                    child: new IconButton(
                                        icon: new Icon(Icons.camera_alt, color: Colors.white),
                                        onPressed: () async {
                                          File selectedImg = await ImagePicker.pickImage(source: ImageSource.gallery);
                                          setState(() { if(selectedImg != null) playgroundImg = selectedImg; });
                                        },
                                        splashColor: Theme.of(context).primaryColor
                                    )
                                )
                            )
                        )
                      ],
                    ),
                  ),

                  // Main part
                  new Column(
                      mainAxisSize: MainAxisSize.max,
                      children: <Widget>[
                        new Padding(
                          padding: EdgeInsets.all(16),
                          child: new Column(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: <Widget>[

                              new Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[

                                  new Padding(
                                      padding: EdgeInsets.only(left: 12),
                                      child: new Text("Nom", style: PlaygroundLabelStyle.getStyle(context))
                                  ),
                                  new Padding(
                                    padding: EdgeInsets.all(12),
                                    child: new TextFormField(
                                        initialValue: newPlayground.name,
                                        style: PlaygroundTextFieldStyle.getStyle(context),
                                        decoration: PlaygroundTextFieldStyle.getDecoration(context, "Terrain Dinton"),
                                        validator : (value) {
                                          if(value.isEmpty) return "Le champ Nom est obligatoire";
                                        },
                                        onSaved: (value) {
                                          newPlayground.name = value.trim();
                                        }
                                    )
                                  ),

                                  new Padding(
                                      padding: EdgeInsets.only(left: 12),
                                      child: new Text("Adresse", style: PlaygroundLabelStyle.getStyle(context))
                                  ),
                                  new Padding(
                                    padding: EdgeInsets.all(12),
                                    child: new TextFormField(
                                        style: PlaygroundTextFieldStyle.getStyle(context),
                                        decoration: PlaygroundTextFieldStyle.getDecoration(context, "24 Rue Anatole Talent"),
                                        validator : (value) {
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
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      children: <Widget>[

                                        new Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: <Widget>[

                                            new Padding(
                                                padding: EdgeInsets.all(4),
                                                child: new Row(
                                                  mainAxisSize: MainAxisSize.max,
                                                  mainAxisAlignment: MainAxisAlignment.start,
                                                  children: [
                                                    new Text("Sports", style: PlaygroundLabelStyle.getStyle(context)),
                                                    new IconButton(icon: new Icon(Icons.add_circle, color: Theme.of(context).primaryColor), onPressed: () {
                                                      _openDialogAddItemSelection(context);
                                                    })
                                                  ]
                                                )
                                            ),

                                            new Padding(
                                              padding: EdgeInsets.all(8),
                                              child: new Row(
                                                mainAxisSize: MainAxisSize.max,
                                                crossAxisAlignment: CrossAxisAlignment.start,
                                                children: <Widget>[
                                                  new Column(
                                                    mainAxisAlignment: MainAxisAlignment.start,
                                                    crossAxisAlignment: CrossAxisAlignment.start,
                                                    mainAxisSize: MainAxisSize.max,
                                                    children: playgroundSportsLeft,
                                                  ),
                                                  new Column(
                                                    mainAxisAlignment: MainAxisAlignment.start,
                                                    crossAxisAlignment: CrossAxisAlignment.start,
                                                    mainAxisSize: MainAxisSize.max,
                                                    children: playgroundSportsRight,
                                                  )
                                                ],
                                              )

                                            ),

                                          ],
                                        ),

                                      ],
                                    ),

                                  ),

                                  new Padding(
                                      padding: EdgeInsets.only(left: 12),
                                      child: new Text("Description", style: PlaygroundLabelStyle.getStyle(context))
                                  ),
                                  new Padding(
                                    padding: EdgeInsets.all(12),
                                    child: new TextFormField(
                                      style: PlaygroundTextFieldStyle.getStyle(context),
                                      decoration: PlaygroundTextFieldStyle.getDecoration(context, "Le terrain offre 4 paniers et deux cages"),
                                      maxLines: 3,
                                      keyboardType: TextInputType.multiline,
                                      onSaved: (value) {
                                        newPlayground.description = value.trim();
                                      },
                                    ),
                                  ),

                                  new Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    crossAxisAlignment: CrossAxisAlignment.center,
                                    children: <Widget>[

                                      new Padding(
                                          padding: EdgeInsets.all(8),
                                          child:  new Row(
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
                                              new Text("Privé", style: PlaygroundLabelStyle.getStyle(context))
                                            ],
                                          )
                                      ),

                                      new Padding(
                                          padding: EdgeInsets.all(8),
                                          child: new Row(
                                            children: <Widget>[
                                              new PlaygroundCheckbox(
                                                  value: newPlayground.covered,
                                                  onChanged: (value) {
                                                    if (value == null) value = false;
                                                    setState(() {
                                                      newPlayground.covered = value;
                                                    });
                                                  }
                                              ),
                                              new Text("Couvert", style: PlaygroundLabelStyle.getStyle(context))
                                            ],
                                          )
                                      )

                                    ],
                                  ),

                                ],
                              ),

                              new Padding(
                                padding: EdgeInsets.all(12),
                                child:new PlaygroundButton(
                                  "Valider",
                                  validateForm
                                )
                              )

                            ],
                          ),
                        )
                      ]
                  )

                ],
              ),
            )
        )
      )
    );

  }

}