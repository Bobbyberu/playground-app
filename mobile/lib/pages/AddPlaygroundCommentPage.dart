
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
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

  Comment newComment;

  @override
  void initState() {
    newComment = Comment.getDefault();
    newComment.playground = widget.playground;
    newComment.author = null; //TODO
    super.initState();
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
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[

                new Padding(
                    padding: EdgeInsets.only(left: 12),
                    child: new Text("Note", style: PlaygroundLabelStyle.getStyle(context))
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
                        validator : (value) {
                          if(value.isEmpty) return "Le champ Commentaire est obligatoire";
                        },
                        onSaved: (value) {
                          newComment.comment = value;
                        }
                    )
                ),

              ],
            )
          ),
        )
        ,
      ),
    );
  }

}