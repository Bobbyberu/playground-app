
import 'dart:io';
import 'dart:convert';
import 'package:Playground/services/AuthService.dart';
import 'package:http/http.dart' as http;

class CommonController {

  static const String baseUrl = "http://localhost:8080/";

  Map<String, String> headers =  {
    "Content-Type" : "application/json",
    "Accept" : "application/json",
    "Authorization" : AuthService.token
  };

  Future<http.Response> get(String url) async {
    var client = new http.Client();
    return client.get(
      url,
      headers: headers
    ).whenComplete(client.close);
  }

  Future post(String url, Map jsonData) async {
    var client = new http.Client();
    return client.post(
      url,
      headers: headers,
      body:  json.encode(jsonData)
    ).whenComplete(client.close);
  }

  Future put(String url, Map jsonData) async {
    var client = new http.Client();
    return client.put(
      url,
      headers: headers,
      body: json.encode(jsonData)
    ).whenComplete(client.close);
  }

  Future delete(String url) async {
    var client = new http.Client();
    return client.delete(
      url,
      headers: headers
    ).whenComplete(client.close);
  }

  void printError(Error error) {
    print("==== Error " + this.runtimeType.toString());
    print(error);
  }
}