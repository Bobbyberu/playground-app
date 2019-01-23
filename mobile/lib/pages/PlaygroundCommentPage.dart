
import 'package:Playground/entities/Comment.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/services/CommentService.dart';
import 'package:Playground/pages/AddPlaygroundCommentPage.dart';
import 'package:Playground/widgets/dialog/PlaygroundDialog.dart';
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

  bool _isLoading;

  @override
  void initState() {
    _isLoading = true;
    comments = new List();
    loadComments();
    super.initState();
  }

  void loadComments() async {
    await commentService.getCommentsOfPlayground(widget.playground.id).then((response){
      setState(() {
        comments = response;
        _isLoading = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> commentItems = List.generate(comments.length, (index) => new CommentCard(comment: comments.elementAt(index)));
    commentItems.add(new Container(height: 200,width: MediaQuery.of(context).size.width));

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
                  Navigator.of(context).push(new MaterialPageRoute(builder: (context) => new AddPlaygroundCommentPage(playground: widget.playground))).then((_) => loadComments());
                }
              ),


              new SingleChildScrollView(
                child: new ConstrainedBox(
                  constraints: new BoxConstraints(minHeight: MediaQuery.of(context).size.height-30, maxHeight: MediaQuery.of(context).size.height-30,  minWidth: MediaQuery.of(context).size.width),

                  child: new Padding(
                    padding: EdgeInsets.all(12),
                    child:
                    (_isLoading) ?
                      new Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          new CircularProgressIndicator(
                            valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).primaryColor),
                          )
                        ],
                      )
                      :
                      (comments.isEmpty) ?
                      new Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          new Padding(padding: EdgeInsets.all(12), child: new Text("Aucun commentaire disponible"))
                        ],
                      ) :
                      new ListView(
                        scrollDirection: Axis.vertical,
                        children: commentItems,
                      ),
                  )

                ),
              ),

            ],
          )
        )
      )
    );
  }

}