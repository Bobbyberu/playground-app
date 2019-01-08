
import 'dart:io';
import 'package:http/http.dart' as http;

class CommonController {

  Map<String, String> headers =  {
    "Content-Type" : "application/json",
    "Accept" : "application/json",
    "Authorization" : ""
  };

  Future<http.Response> get(String url) async {
    return http.get(
      url,
      headers: headers
    );
  }

  Future post(String url, Map jsonData) async {
    return http.post(
      url,
      headers: headers,
      body:  jsonData
    );
  }

  Future put(String url, Map jsonData) async {
    return http.put(
      url,
      headers: headers,
      body: jsonData
    );
  }

  Future delete(String url) async {
    return http.delete(
      url,
      headers: headers)
    ;
  }

}