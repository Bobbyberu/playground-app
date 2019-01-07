import 'package:flutter/material.dart';


class SettingsMenuLink extends StatelessWidget {

  final String label;
  final IconData icon;
  final VoidCallback onTap;

  const SettingsMenuLink({Key key, this.label, this.icon, this.onTap}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new InkWell(
      child: new Padding(
          padding: EdgeInsets.all(12),
          child: new Row(
            children: <Widget>[
              new Padding(
                  padding: EdgeInsets.only(right: 12),
                  child:new Icon(icon, color: Colors.grey[800])
              ),
              new Text(
                  label,
                  style: new TextStyle(
                      fontSize: 20,
                      color: Colors.grey[800]
                  )
              ),
            ],
          )
      ),
      onTap: onTap
    );
  }

}