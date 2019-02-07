
import 'dart:convert';
import 'package:flutter/services.dart';
import 'package:location/location.dart';

import 'package:Playground/controllers/CommonController.dart';
import 'package:latlong/latlong.dart';

class LocationService {

  /// Members
  static const String API_URL = "https://nominatim.openstreetmap.org/search?limit=3&format=json&q=";

  static CommonController _controller = new CommonController();

  static var location = new Location();


  /// Methods
  static Future<LatLng> getCoordOfAddress(String address) async {

    String args = Uri.encodeFull(address);

    LatLng coords = null;

    await _controller.get(API_URL + args).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        var data = json.decode(response.body) as List<dynamic>;
        if(data[0] != null && data[0]["lat"] != null && data[0]["lon"] != null) {
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

  static Future<String> getAddressOfCoord(LatLng coords) async {
    String address = null;

    await _controller.get("https://nominatim.openstreetmap.org/reverse?format=json&lat=" + coords.latitude.toString() + "&lon=" + coords.longitude.toString() + "&zoom=18&addressdetails=1").then((response){
      var data = json.decode(response.body);
      if(data["address"] != null) {
        if(data["address"]["house_number"] != null) {
          address = data["address"]["house_number"] +  " " + data["address"]["road"] + " " + data["address"]["city"] + " " + data["address"]["postcode"] + ", " + data["address"]["country"];
        } else {
          address = data["address"]["road"] + " " + data["address"]["city"] + " " + data["address"]["postcode"] + ", " + data["address"]["country"];
        }
      }
    }).catchError((error){
      _controller.printError(error);
    });

    return address;
  }

  /// Methods
  static Future<List<Address>> getPredictionsOfAddress(String address) async {

    String args = Uri.encodeFull(address);

    List<Address> predictions = new List();

    await _controller.get(API_URL + args).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        var data = json.decode(response.body) as List<dynamic>;
        data.forEach((dataRow) {
          predictions.add(Address(new LatLng(double.parse(data[0]["lat"]), double.parse(data[0]["lon"])), dataRow["display_name"]));
        });
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return predictions;
  }

  static Future<LatLng> getLocation() async {
    LatLng currentLocation;
    var data;
    try {
      data = await location.getLocation();
    } on PlatformException {
      currentLocation = null;
    }
    if(data["latitude"] != null && data["longitude"] != null) {
      currentLocation = new LatLng(data["latitude"], data["longitude"]);
    }
    return currentLocation;
  }

}

class Address {

  final LatLng coords;
  final String address;

  Address(this.coords, this.address);

}