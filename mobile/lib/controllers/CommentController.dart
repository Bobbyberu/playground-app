
import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/entities/Comment.dart';

class CommentController extends CommonController {

  static const String route = CommonController.baseUrl + "comments/";

  Future getCommentById(int id){
    return get( Uri.encodeFull(route + id.toString()) );
  }

}