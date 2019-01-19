
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/SignalPlayground.dart';
import 'package:Playground/entities/User.dart';
import 'package:Playground/enums/SignalPlaygroundMotives.dart';
import 'package:Playground/pages/PlaygroundDetails.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/services/SignalPlaygroundService.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:flutter/material.dart';


///
/// This page is a form to post a playground signalement
///
class SignalPlaygroundPage extends StatefulWidget {

  final Playground signaledPlayground;

  SignalPlaygroundPage(this.signaledPlayground);

  @override
  State<StatefulWidget> createState() => SignalPlaygroundPageState();

}

class SignalPlaygroundPageState extends State<SignalPlaygroundPage> {

  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  final SignalPlaygroundService signalPlaygroundService = new SignalPlaygroundService();
  SignalPlayground newSignal;

  static const Map<SignalPlaygroundMotives, String> motives = {
    SignalPlaygroundMotives.CLOSED_PLAYGROUND : "Playground condamné",
    SignalPlaygroundMotives.FALSE_ADDRESS : "Fausse adresse renseignée",
    SignalPlaygroundMotives.NOT_A_PLAUGROUND : "Ce n'est pas un playground",
    SignalPlaygroundMotives.OTHER : "Autre"
  };
  List<DropdownMenuItem<String>> motiveItems;
  String selectedMotive;


  @override
  void initState() {
    newSignal = SignalPlayground.getDefault();
    newSignal.playground = widget.signaledPlayground;
    User me = SessionManager.getInstance().getUser();
    newSignal.author = me;

    motiveItems = new List();
    motives.forEach((key,value) {
      motiveItems.add(new DropdownMenuItem<String>(child: new Text(value), value: value));
    });
    selectedMotive = motives[SignalPlaygroundMotives.CLOSED_PLAYGROUND];

    super.initState();
  }


  void validateForm() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();

      bool success = await signalPlaygroundService.saveSignalPlayground(newSignal);

      if(success) {
        PlaygroundDialog.showValidDialog(
            context,
            "Envoi de votre signalement",
            "Votre signalement a bien été envoyé.",
            () {Navigator.pop(context);Navigator.pop(context);}
        );
      } else {
        PlaygroundDialog.showErrorDialog(
            context,
            "Envoi de votre signalement",
            "Un problème est venu lors de la validation. Veuillez réessayer plus tard.",
            () {Navigator.pop(context);}
        );
      }
    }
  }


  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Signaler ce playground"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),
          child: new Form(
            key: _formKey,
            child: new Padding(
              padding: EdgeInsets.only(top: 24, left: 12, right: 12, bottom: 12),
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[

                  new Padding(
                      padding: EdgeInsets.only(left: 12),
                      child: new Text("Raison", style: PlaygroundLabelStyle.getStyle(context))
                  ),
                  new Padding(
                    padding: EdgeInsets.all(12),
                    child: new DropdownButtonFormField(
                      items: motiveItems,
                      value: selectedMotive,
                      onChanged: (value) {
                        setState(() {
                          selectedMotive = value;
                        });
                      },
                      onSaved: (value) {
                        newSignal.motive = value;
                      },
                      decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
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
                      decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                      maxLines: 3,
                      keyboardType: TextInputType.multiline,
                      validator : (value) {
                        if(value.isEmpty) return "Le champ Description est obligatoire";
                      },
                      onSaved: (value) {
                        newSignal.description = value.trim();
                      }
                    )
                  ),

                  new Padding(
                    padding: EdgeInsets.all(12),
                    child: new Row(
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children : [
                        new PlaygroundButton(
                          "Envoyer",
                          validateForm
                        )
                      ]
                    )
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