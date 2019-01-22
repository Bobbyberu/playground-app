
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/Sport.dart';
import 'package:Playground/services/PlaygroundService.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundCheckbox.dart';
import 'package:Playground/widgets/inputs/PlaygroundSportSelection.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:flutter/material.dart';

class AddPlaygroundPage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => AddPlaygroundPageState();

}

class AddPlaygroundPageState extends State<AddPlaygroundPage> {

  Playground newPlayground = Playground.getDefault();
  int currentStep;
  int realStep;

  // FORM FIELDS KEYS
  final GlobalKey<FormState> _nameKey = new GlobalKey<FormState>();
  final GlobalKey<FormState> _addressKey = new GlobalKey<FormState>();
  final GlobalKey<FormState> _sportsKey = new GlobalKey<FormState>();
  final GlobalKey<FormState> _descriptionKey = new GlobalKey<FormState>();


  @override
  void initState() {
    super.initState();
    currentStep = 0;
    realStep = 0;
  }


  @override
  Widget build(BuildContext context) {

    return new Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.green,
        title: new Text("Nouveau Playground"),
      ),

      body: new Stepper(
        currentStep: this.currentStep,
        steps: getFormSteps(),
        type: StepperType.horizontal,
        controlsBuilder: (BuildContext context, {VoidCallback onStepContinue, VoidCallback onStepCancel}) {
          return Padding(
            padding: EdgeInsets.only(top: 18),
            child:Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                FlatButton(
                    onPressed: onStepCancel,
                    child: new Text("RETOUR", style: new TextStyle(color: Colors.grey))
                ),
                RaisedButton(
                  color: Theme.of(context).primaryColor,
                  child: new Text("SUIVANT",style: new TextStyle(color: Colors.white)),
                  onPressed: onStepContinue,
                ),
              ],
            )
          );
        },
        onStepTapped: (step) {
          if (step <= realStep) {
            setState(() {
              currentStep = step;
            });
          }
        },
        onStepContinue: () {
          validateStep();
        },
        onStepCancel: () {
          setState(() {
            if (currentStep > 0) {
              this.currentStep--;
            }
          });
        },
      )
    );

  }

  List<Step> getFormSteps() {
    List<Step> _steps = [
      getNameStep(),

      getAddressStep(),

      getSportStep(),

      getDescriptionStep(),

      getValidationStep()
    ];
    return _steps;
  }


  Step getNameStep() {
    return Step(
        title: new Icon(Icons.sentiment_satisfied, color: Colors.grey, size: 16),
        isActive: currentStep >= 0,
        content: new Form(
            key: _nameKey,
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Padding(
                    padding: EdgeInsets.only(bottom: 12),
                    child: new Text("Donnez un nom à votre Playground", textAlign: TextAlign.center, style: PlaygroundFormTitleStyle.getStyle(context))
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
              ],
            )
        )
    );
  }

  Step getAddressStep() {
    return Step(
        title: new Icon(Icons.add_location, color: Colors.grey, size: 16),
        isActive: currentStep >= 1,
        content: new Form(
            key: _addressKey,
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Padding(
                    padding: EdgeInsets.only(bottom: 12),
                    child: new Text("Où se situe votre Playground ?", style: PlaygroundFormTitleStyle.getStyle(context))
                ),
                new Padding(
                    padding: EdgeInsets.all(12),
                    child: new TextFormField(
                        initialValue: newPlayground.address,
                        style: PlaygroundTextFieldStyle.getStyle(context),
                        decoration: PlaygroundTextFieldStyle.getDecoration(context, "24 Rue du plaisir"),
                        validator : (value) {
                          print("value : " + value);
                          if(value.isEmpty) return "Le champ Adresse est obligatoire";
                        },
                        onSaved: (value) {
                          newPlayground.address = value.trim();
                        }
                    )
                ),
              ],
            )
        )
    );
  }

  Step getSportStep() {
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

    return Step(
        title: new Icon(Icons.terrain, color: Colors.grey, size: 16),
        isActive: currentStep >= 2,
        content: new Form(
            key: _addressKey,
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Padding(
                    padding: EdgeInsets.only(bottom: 12),
                    child: new Text("Quels sport sont pratiquables sur votre Playground ?", textAlign: TextAlign.center, style: PlaygroundFormTitleStyle.getStyle(context))
                ),
                new Padding(
                    padding: EdgeInsets.all(12),
                    child: new InkWell(
                        child: new Row(
                            mainAxisSize: MainAxisSize.max,
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              new Padding(padding: EdgeInsets.all(4), child: new Icon(Icons.add_circle, color: Theme.of(context).primaryColor)),
                              new Text("Ajouter des sports", style: PlaygroundLabelStyle.getStyle(context))
                            ]
                        ),
                      onTap: () {
                        _openDialogAddItemSelection(context);
                      },
                    )
                ),

                new Padding(
                    padding: EdgeInsets.all(8),
                    child: new Row(
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
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
            )
        )
    );
  }

  Step getDescriptionStep() {
    return Step(
        title: new Icon(Icons.description, color: Colors.grey, size: 16),
        isActive: currentStep >= 3,
        content: new Form(
            key: _descriptionKey,
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                new Padding(
                    padding: EdgeInsets.only(bottom: 12),
                    child: new Text("Description de votre Playground", textAlign: TextAlign.center, style: PlaygroundFormTitleStyle.getStyle(context))
                ),
                new Padding(
                    padding: EdgeInsets.all(12),
                    child: new TextFormField(
                      initialValue: newPlayground.description,
                      style: PlaygroundTextFieldStyle.getStyle(context),
                      maxLines: 3,
                      keyboardType: TextInputType.multiline,
                      decoration: PlaygroundTextFieldStyle.getDecoration(context, "Que comporte ce playground ? Infrastructures, règles, ..."),
                      onSaved: (value) {
                        newPlayground.description = value.trim();
                      }
                    )
                ),

                new Padding(
                  padding: EdgeInsets.all(12),
                  child: new Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: <Widget>[
                      new Text("Ce playground est-il privé ?", style: PlaygroundLabelStyle.getStyle(context)),
                      new PlaygroundCheckbox(
                          value: newPlayground.isPrivate,
                          onChanged: (value) {
                            if (value == null) value = false;
                            setState(() {
                              newPlayground.isPrivate = value;
                            });
                          }
                      ),
                    ],
                  ),
                ),

                new Padding(
                  padding: EdgeInsets.all(12),
                  child: new Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: <Widget>[
                      new Text("Ce playground est-il couvert ?", style: PlaygroundLabelStyle.getStyle(context)),
                      new PlaygroundCheckbox(
                          value: newPlayground.covered,
                          onChanged: (value) {
                            if (value == null) value = false;
                            setState(() {
                              newPlayground.covered = value;
                            });
                          }
                      ),
                    ],
                  ),
                )
              ],
            )
        )
    );
  }

  Step getValidationStep() {
    return new Step(
      title:new Icon(Icons.check, color: Colors.grey, size: 16),
      isActive: currentStep >= 4,
      content: new Form(
        child: new Column(
          children: <Widget>[

            new Padding(
                padding: EdgeInsets.only(bottom: 12),
                child: new Icon(Icons.tag_faces, color: Colors.grey, size: 64)
            ),

            new Padding(
                padding: EdgeInsets.only(bottom: 12),
                child: new Text("Votre Playground est prêt à être validé", textAlign: TextAlign.center, style: PlaygroundFormTitleStyle.getStyle(context))
            ),

            new Padding(
                padding: EdgeInsets.only(bottom: 12),
                child: new Text("Appuyer sur suivant pour valider", textAlign: TextAlign.center, style: PlaygroundLabelStyle.getStyle(context))
            ),

          ],
        ),
      )
    );
  }

  ///
  /// Function called each time the next step is called. It validates the current form.
  /// If validated, the form go to the next step
  ///
  void validateStep() {
    switch(currentStep) {
      case 0: // NAME
        if (_nameKey.currentState.validate()) {
          _nameKey.currentState.save();
          setState(() { currentStep++; if(realStep < 1) realStep = 1; });
        }
        break;
      case 1: // ADRESSE
        if (_addressKey.currentState.validate()) {
          _addressKey.currentState.save();
          setState(() { currentStep++; if(realStep < 2) realStep = 2; });
        }
        break;
      case 2: // SPORTS
        if (newPlayground.sports.length > 0) {
          setState(() { currentStep++; if(realStep < 3) realStep = 3; });
        }
        break;
      case 3: // DETAILS
        if (_descriptionKey.currentState.validate()) {
          _descriptionKey.currentState.save();
          setState(() { currentStep++; if(realStep < 4) realStep = 4; });
        }
        break;
      case 4: // VALIDATION
        if (Playground.validate(newPlayground)) {
          validatePlayground();
        } else {
          PlaygroundDialog.showWarningDialog(context, "Problème de validation", "Vérifiez que vos renseignements soient corrects", () { Navigator.of(context).pop(); });
        }
        break;
    }
  }

  ///
  ///Navigate to a new component to select and return a list of sports
  ///
  Future _openDialogAddItemSelection(BuildContext context) async {

    Set<Sport> sports = await Navigator.of(context).push(
        new MaterialPageRoute<Set<Sport>>(
          builder: (BuildContext context) {
            return new PlaygroundSportSelection(newPlayground);
          },
          fullscreenDialog: true
        )
    );

    setState(() {
      if(sports != null) {
        newPlayground.sports.clear();
        newPlayground.sports = sports;
      }
    });
  }

  ///
  /// Save the playground
  /// Display success to the user
  ///
  void validatePlayground() async {
    PlaygroundService playgroundService = new PlaygroundService();
    await playgroundService.save(newPlayground, null).then((result) {
      if (result) {
        PlaygroundDialog.showValidDialog(
            context,
            "Validation",
            "Le nouveau playground a été ajouté !",
                () {Navigator.pushReplacementNamed(context, '/home');}
        );
      } else {
        PlaygroundDialog.showErrorDialog(
            context,
            "Validation",
            "Un problème est venu lors de la validation. Veuillez réessayer plus tard.",
                () {Navigator.pop(context);}
        );
      }
    });
  }

}