
import 'package:flutter/material.dart';

class PlaygroundDialog {

  static _customDialog(BuildContext context, String title, String body, VoidCallback onOk, IconData icon, Color color) {
    showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return _getCustomAlertDialog(context, title, body, onOk, icon, color);
        }
    );
  }

  static AlertDialog _getCustomAlertDialog(BuildContext context, String title, String body, VoidCallback onOk, IconData icon, Color color) {
    return new AlertDialog(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(5.0))),
        titlePadding: EdgeInsets.all(0),
        title: new Material(
          color: color,
          borderRadius: BorderRadius.only(topLeft: Radius.circular(5), topRight: Radius.circular(5)),
          child: new Padding(
            padding: EdgeInsets.only(top: 24, bottom: 24, left: 12, right: 12),
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[

                new Padding(
                  padding: EdgeInsets.all(8),
                  child: new Icon(icon,
                    color: Colors.white,
                    size: 56,
                  ),
                ),

                new Padding(
                  padding: EdgeInsets.all(8),
                  child: new Text(
                    title,
                    textAlign: TextAlign.center,
                    style: new TextStyle(
                        color: Colors.white,
                        fontSize: 20
                    ),
                  ),
                ),

                new Padding(
                  padding: EdgeInsets.all(8),
                  child: new Text(
                    body,
                    textAlign: TextAlign.center,
                    style: new TextStyle(
                      color: Colors.white,
                    ),
                  ),
                )

              ],
            ),
          ),
        ),
        contentPadding: EdgeInsets.all(0),
        content: new Material(
            color: color,
            child: new Padding(
                padding: EdgeInsets.all(8),
                child: new Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  mainAxisSize: MainAxisSize.max,
                  children: <Widget>[
                    new FlatButton(
                      child: new Text("Ok", style: new TextStyle(color: Colors.white, fontSize: 18)),
                      onPressed: onOk,
                    )
                  ],
                )
            )
        )
    );
  }



  static showValidDialog(BuildContext context, String title, String body, VoidCallback onOk) {
    _customDialog(context, title, body, onOk, Icons.check_circle_outline, Theme.of(context).primaryColor);
  }

  static showWarningDialog(BuildContext context, String title, String body, VoidCallback onOk) {
    _customDialog(context, title, body, onOk, Icons.warning, Colors.orange[600]);
  }

  static showErrorDialog(BuildContext context, String title, String body, VoidCallback onOk) {
    _customDialog(context, title, body, onOk, Icons.error_outline, Colors.red[600]);
  }

  static showNoConnectionDialog(BuildContext context, VoidCallback onOk) {
    _customDialog(context, "Oh oh...", "Nous ne parvenons pas à vous connecter à l'application. Veuillez vérifier votre connexion Internet", onOk, Icons.signal_wifi_off, Colors.grey[900]);
  }

  static AlertDialog getNoConnectionAlertDialog(BuildContext context, VoidCallback onOk) {
    return _getCustomAlertDialog(context, "Oh oh...", "Nous ne parvenons pas à vous connecter à l'application. Veuillez vérifier votre connexion Internet", onOk, Icons.signal_wifi_off, Colors.grey[800]);
  }
}