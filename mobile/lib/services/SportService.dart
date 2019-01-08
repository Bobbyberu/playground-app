
import 'package:Playground/entities/Sport.dart';

class SportService {

  List<Sport> getSports() {
    List<Sport> sports = new List<Sport>();
    sports.add(new Sport(1, "Basketball"));
    sports.add(new Sport(2, "Football"));
    sports.add(new Sport(3, "Volleyball"));
    sports.add(new Sport(4, "Skateboard"));
    sports.add(new Sport(5, "Baseball"));
    sports.add(new Sport(6, "Badminton"));
    sports.add(new Sport(7, "Tennis"));
    sports.add(new Sport(8, "Hockey"));

    return sports;
  }

}