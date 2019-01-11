
import 'dart:io';
import 'dart:convert';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/services/TokenManager.dart';
import 'package:http/http.dart' as http;

class CommonController {

  static const String baseUrl = "http://localhost:8080/api/";

  Future<Map<String, String>> _getHeaders() async{
    String token = await TokenManager.getInstance().getToken();

    var headers =  {
      "Content-Type" : "application/json",
      "Accept" : "application/json"
    };

    if (token != null) {
      headers["Authorization"] = token;
    }

    print(headers);
    return headers;
  }


  Future<http.Response> get(String url) async {
    print("GET " + url);
    var client = new http.Client();
    return client.get(
      url,
      headers: await _getHeaders()
    ).whenComplete(client.close);
  }

  Future post(String url, Map jsonData) async {
    print("POST " + url);
    var client = new http.Client();
    return client.post(
      url,
      headers: await _getHeaders(),
      body:  json.encode(jsonData)
    ).whenComplete(client.close);
  }

  Future put(String url, Map jsonData) async {
    print("PUT " + url);
    var client = new http.Client();
    return client.put(
      url,
      headers: await _getHeaders(),
      body: json.encode(jsonData)
    ).whenComplete(client.close);
  }

  Future delete(String url) async {
    print("DELETE " + url);
    var client = new http.Client();
    return client.delete(
      url,
      headers: await _getHeaders()
    ).whenComplete(client.close);
  }

  void printError(Error error) {
    print("==== Error " + this.runtimeType.toString());
    print(error);
  }
}