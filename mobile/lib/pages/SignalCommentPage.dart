
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/SignalComment.dart';
import 'package:Playground/entities/User.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/services/SignalCommentService.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:flutter/material.dart';

class SignalCommentPage extends StatefulWidget {

  final Comment comment;

  const SignalCommentPage({Key key, this.comment}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new SignalCommentPageState();

}

class SignalCommentPageState extends State<SignalCommentPage> {

  SignalCommentService signalCommentService = new SignalCommentService();
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  SignalComment signal;

  @override
  void initState() {
    signal = SignalComment.getDefault();
    User me = SessionManager.getInstance().getUser();
    signal.author = me;
    signal.comment = widget.comment;
    super.initState();
  }

  void validateForm() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();

      bool success = await signalCommentService.save(signal);

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
        title: new Text("Signaler un commentaire"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),
          child: new Form(
            key: _formKey,
            child: new Padding(
              padding: EdgeInsets.all(12),
              child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[

                  new Card(
                    child: new Padding(
                      padding: EdgeInsets.all(12),
                      child: new Text(
                        "“" + widget.comment.comment + "“",
                        textAlign: TextAlign.justify,
                        style: new TextStyle(
                          fontStyle: FontStyle.italic,
                        ),
                      ),
                    ),
                  ),

                  new Padding(
                      padding: EdgeInsets.only(top: 12, left: 12),
                      child: new Text("Raison", style: PlaygroundLabelStyle.getStyle(context))
                  ),
                  new Padding(
                    padding: EdgeInsets.all(12),
                    child: new TextFormField(
                      style: PlaygroundTextFieldStyle.getStyle(context),
                      decoration: PlaygroundTextFieldStyle.getDecoration(context, "Expliquer les raisons de votre signalement"),
                      maxLines: 3,
                      keyboardType: TextInputType.multiline,
                      onSaved: (value) {
                        signal.description = value.trim();
                      },
                    ),
                  ),

                  new Row(
                    mainAxisSize: MainAxisSize.max,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[

                      new Padding(
                          padding: EdgeInsets.all(12),
                          child: new PlaygroundButton(
                              "Signaler",
                              validateForm
                          )
                      )

                    ],
                  )

                ],
              ),
            )
          ),
        ),
      ),
    );
  }


}