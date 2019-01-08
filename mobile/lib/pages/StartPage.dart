import 'package:Playground/pages/LoginPage.dart';
import 'package:Playground/pages/MainPage.dart';
import 'package:flutter/material.dart';

/**
 * Entry point of the application
 * The start page is the first widget the user is redirected to
 * Depending on if the user is connected or not it will redirect to home page or to login page
 */
class StartPage extends StatelessWidget{

  @override
  Widget build(BuildContext context) {
    //return new MainPage();
    return new LoginPage();
  }

}