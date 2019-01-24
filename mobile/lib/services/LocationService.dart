
import 'dart:convert';

import 'package:Playground/controllers/CommonController.dart';
import 'package:latlong/latlong.dart';

class LocationService {

  static const String API_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

  static CommonController _controller = new CommonController();

  static Future<LatLng> getCoordOfAddress(String address) async {

    String args = Uri.encodeFull(address);

    LatLng coords = null;

    await _controller.get(API_URL + args).then((response) {
      _controller.printResponse(response);
      if(response.statusCode != null && response.statusCode == 200) {
        var data = json.decode(response.body) as List<dynamic>;
        if(data[0] != null && data[0]["lat"] != null && data[0]["lon"] != null && data.length < 3) {
          double lat = double.parse(data[0]["lat"]);
          double lon = double.parse(data[0]["lon"]);
          coords = new LatLng(lat,lon);
        }
      }
    }).catchError((error) {
      _controller.printError(error);
      coords = null;
    });

    return coords;
  }

}