
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:Playground/services/CommentService.dart';
import 'package:Playground/services/SessionManager.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
import 'package:Playground/widgets/inputs/PlaygroundButton.dart';
import 'package:Playground/widgets/playground/PlaygroundMarkSelector.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:Playground/widgets/style/PlaygroundLabelStyle.dart';
import 'package:flutter/material.dart';

class AddPlaygroundCommentPage extends StatefulWidget {

  final Playground playground;

  const AddPlaygroundCommentPage({Key key, this.playground}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new AddPlaygroundCommentState();

}

class AddPlaygroundCommentState extends State<AddPlaygroundCommentPage> {

  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  CommentService commentService = new CommentService();
  Comment newComment;

  @override
  void initState() {
    newComment = Comment.getDefault();
    newComment.playground = widget.playground;
    User me = SessionManager.getInstance().getUser();
    newComment.author = me;
    super.initState();
  }

  void validateForm() async {
    if (_formKey.currentState.validate()) {
      _formKey.currentState.save();

      bool success = await commentService.save(newComment);

      if(success) {
        PlaygroundDialog.showValidDialog(
          context,
          "Envoi de votre commentaire",
          "Votre commentaire a bien été envoyé.",
          () {Navigator.pop(context); Navigator.pop(context);}
        );
      } else {
        PlaygroundDialog.showErrorDialog(
          context,
          "Envoi de votre commentaire",
          "Un problème est venu lors de la validation. Veuillez réessayer plus tard.",
          () { Navigator.pop(context); }
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Ajouter un commentaire"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),
          child: new Padding(
            padding: EdgeInsets.all(12),
            child: new Form(
                key: _formKey,
                child: new Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[

                    new Padding(
                        padding: EdgeInsets.only(left: 12),
                        child: new Text("Note", style: PlaygroundLabelStyle.getStyle(context))
                    ),
                    new Padding(
                      padding: EdgeInsets.all(8),
                      child: new PlaygroundMarkSelector(
                        initialValue: 3,
                        onSaved: (value) {
                          newComment.mark = value.ceilToDouble();
                        },
                      ),
                    ),

                    new Padding(
                        padding: EdgeInsets.only(left: 12),
                        child: new Text("Commentaire", style: PlaygroundLabelStyle.getStyle(context))
                    ),
                    new Padding(
                        padding: EdgeInsets.all(12),
                        child: new TextFormField(
                            style: PlaygroundTextFieldStyle.getStyle(context),
                            decoration: PlaygroundTextFieldStyle.getDecoration(context, ""),
                            keyboardType: TextInputType.multiline,
                            maxLines: 3,
                            validator : (value) {
                              if(value.isEmpty) return "Le champ Commentaire est obligatoire";
                            },
                            onSaved: (value) {
                              newComment.comment = value;
                            }
                        )
                    ),

                    new Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        new Padding(
                          padding: EdgeInsets.all(12),
                          child: new PlaygroundButton(
                              "Envoyer",
                              validateForm
                          )
                        )
                        

                      ],
                    )
                  ],
                )
            )

          ),
        )
        ,
      ),
    );
  }

}