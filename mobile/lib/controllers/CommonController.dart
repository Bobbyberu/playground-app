
import 'dart:io';
import 'dart:convert';
import 'package:Playground/services/AuthService.dart';
import 'package:Playground/services/TokenManager.dart';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';
import 'package:http_parser/http_parser.dart';

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

  Future postFile(String url, File file) async {
    String fileName = file.path.split("/").last;
    print("POST " + url + " " + fileName);

    var headers = await _getHeaders();
    headers["Content-Type"] = "multipart/form-data; boundary=--xXPlayground1234Xx";
    headers["Content-Disposition"] = "form-data; name='file'; filename='" +  fileName + "'";
    headers["Content-Length"] = file.readAsBytesSync().length.toString();
    headers["Accept"] = "*/*";


    // TEST 2
    /*
    var request = new http.MultipartRequest("POST", Uri.parse(url));

    request.files.add(await http.MultipartFile.fromPath(
      "file",
      file.path,
      filename: fileName,
      contentType: new MediaType("image", fileName.split(".").last)
    ));

    return request.send();
    */

    // TEST 1
    UploadFileInfo fileInfo = new UploadFileInfo(file, fileName, contentType: ContentType("image", fileName.split(".").last));

    Dio dio = new Dio();
    Options options = new Options(
      headers: headers,
      connectTimeout: 5000,
      receiveTimeout: 5000,
      contentType: ContentType("multipart", "form-data")
    );

    http.MultipartFile multipart = http.MultipartFile.fromBytes(fileName, file.readAsBytesSync());

    FormData formData = new FormData.from({
      "file": multipart
    });

    return dio.post(url, data: formData, options: options);

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

  void printResponse(http.Response response) {
    print("==== Response " + this.runtimeType.toString());
    print(response.statusCode.toString() + " " + response.reasonPhrase);
    print(response.body);
  }
}