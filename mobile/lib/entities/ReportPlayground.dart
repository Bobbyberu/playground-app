
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:json_annotation/json_annotation.dart';

class ReportPlayground {

  int id;
  String motive;
  String description;
  User author;
  Playground playground;

  ReportPlayground({this.id, this.motive, this.description, this.author, this.playground});

  factory ReportPlayground.getDefault(){
    return new ReportPlayground(
      id: 0,
      motive: "",
      description: "",
      author: null,
      playground: null
    );
  }

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'id': this.id,
      'motive': this.motive,
      'description': this.description,
      'author': (this.author == null) ? null : this.author.toJson(),
      'playground': (this.playground == null) ? null : this.playground.toJson()
    };
  }
}