import 'package:json_annotation/json_annotation.dart';

part 'Sport.g.dart';

/**
 * Sport entity class
 */
@JsonSerializable(nullable: false)
class Sport implements Comparable<Sport>{

  final int id;
  final String name;

  Sport({this.id, this.name});

  factory Sport.fromJson(Map<String, dynamic> json) => _$SportFromJson(json);
  Map<String, dynamic> toJson() => _$SportToJson(this);

  factory Sport.createNew(int id, String name) {
    var s = new Sport(
      id: id,
      name : name
    );

    return s;
  }

  @override
  int compareTo(Sport other) {
    return id.compareTo(other.id);
  }

  @override
  bool operator ==(other) {
    return this.id==other.id && this.name==other.name;
  }

  @override
  int get hashCode {
    return this.id.hashCode * this.name.hashCode;
  }

}