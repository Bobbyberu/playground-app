
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:json_annotation/json_annotation.dart';

class SignalPlayground {

  int id;
  String motive;
  String description;
  User author;
  Playground playground;

  SignalPlayground({this.id, this.motive, this.description, this.author, this.playground});

  factory SignalPlayground.getDefault(){
    return new SignalPlayground(
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