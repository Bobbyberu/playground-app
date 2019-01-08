/**
 * Sport entity class
 */
class Sport implements Comparable<Sport>{

  int id;
  String name;

  Sport(this.id, this.name);

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