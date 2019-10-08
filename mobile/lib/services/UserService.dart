
import 'dart:convert';
import 'dart:io';

import 'package:Playground/controllers/CommonController.dart';
import 'package:Playground/controllers/UserController.dart';
import 'package:Playground/entities/Playground.dart';
import 'package:Playground/entities/User.dart';
import 'package:http/http.dart';

class UserService {

  static String getUserAvatarUrl(User user) {
    if(user.mail != null)
      return CommonController.baseUrl + "users/" + user.mail + "/image";
    else
      return CommonController.baseUrl + "users/" + user.id.toString() + "/image";
  }

  UserController _controller = new UserController();

  Future<bool> updateUser(User user) async {
    bool success = false;

    await _controller.putUser(user).then((response) {
      _controller.printResponse(response);
      if(response.statusCode != null && response.statusCode == 200) {
        User updatedUser = User.fromJson(json.decode(response.body));
        success = (user.username == updatedUser.username) && (user.mail == updatedUser.mail);
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return success;
  }

  Future<Set<Playground>> getFavouritesPlaygrounds() async {
    Set<Playground> playgrounds = new Set();

    await _controller.getFavouritePlaygrounds().then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        List<dynamic> playgroundsJson = json.decode(response.body.toString());
        print(playgroundsJson);
        playgroundsJson.forEach((p) {
          Playground pg = Playground.fromJson(p);
          playgrounds.add(pg);
        });
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return playgrounds;
  }

  Future<bool> checkIfFavorite(int playgroundId) async {
    bool favorite = false;

    await _controller.getPlaygroundFavorite(playgroundId).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        favorite = json.decode(response.body) as bool;
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return favorite;
  }

  Future<bool> togglePlaygroundFavorite(int playgroundId) async {
    bool favorite = false;

    await _controller.putPlaygroundFavorite(playgroundId).then((response) {
      if(response.statusCode != null && response.statusCode == 200) {
        favorite = json.decode(response.body) as bool;
      }
    }).catchError((error) {
      _controller.printError(error);
    });

    return favorite;
  }

  Future<bool> changePassword(String newPassword) async {
    bool res = false;

    return res;
  }


  Future<bool> uploadAvatar(File avatar) async {
    bool success = false;
    
    await _controller.postImage(avatar).then((response) {
      StreamedResponse res = response as StreamedResponse;
      success = res.statusCode != null && res.statusCode == 200;
    }).catchError((error) {
      print(error);
      _controller.printError(error);
    });
    
    return success;
  }



}