
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/services/CommentService.dart';
import 'package:Playground/pages/AddPlaygroundCommentPage.dart';
import 'package:Playground/widgets/playground/CommentCard.dart';
import 'package:flutter/material.dart';


class PlaygroundCommentPage extends StatefulWidget {

  final Playground playground;

  const PlaygroundCommentPage({Key key, this.playground}) : super(key: key);

  @override
  State<StatefulWidget> createState() => new PlaygroundCommentPageState();

}

class PlaygroundCommentPageState extends State<PlaygroundCommentPage> {

  CommentService commentService = new CommentService();
  List<Comment> comments;

  @override
  void initState() {
    comments = new List();
    loadComments();
    super.initState();
  }

  void loadComments() async {
    await commentService.getCommentsOfPlayground(widget.playground.id).then((response){
      setState(() {
        comments = response;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> commentItems = List.generate(comments.length, (index) => new CommentCard(comment: comments.elementAt(index)));

    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Commentaires"),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(),
          child: new Column(
            children: <Widget>[

              new InkWell(
                child: new Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    new Padding(
                      padding: EdgeInsets.all(18),
                      child: new Icon(Icons.add_comment, color: Colors.grey[700], size: 30)
                    ),

                    new Padding(
                      padding: EdgeInsets.only(left: 2),
                      child: new Text("Ajouter un commentaire"),
                    )
                  ],
                ),
                onTap: () {
                  Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new AddPlaygroundCommentPage(playground: widget.playground)));
                }
              ),


              new SingleChildScrollView(
                child: new ConstrainedBox(
                  constraints: new BoxConstraints(minHeight: MediaQuery.of(context).size.height, maxHeight: MediaQuery.of(context).size.height,  minWidth: MediaQuery.of(context).size.width),
                  child: (comments.isEmpty ) ?
                    new Text("Aucun commentaire disponible") :
                    new ListView(
                      scrollDirection: Axis.vertical,
                      children: commentItems,
                    ),
                ),
              ),

            ],
          )
        )
      )
    );
  }

}