
import 'dart:convert';

import 'package:Playground/controllers/SportController.dart';
import 'package:Playground/entities/Sport.dart';

class SportService {

  SportController _controller = new SportController();

  ///
  /// Retrieve all sports
  ///
  Future<Set<Sport>> getSports() async {

    Set<Sport> sports = new Set<Sport>();

    await _controller.getAllSports().then((response) {

      if(response.statusCode == 200){
        List<dynamic> sportsJson = json.decode(response.body);

        sportsJson.forEach((s) {
          Sport sport = Sport.fromJson(s);
          sports.add(sport);
        });
      }

    }).catchError((error){
      _controller.printError(error);
    });

    return sports;
  }

}