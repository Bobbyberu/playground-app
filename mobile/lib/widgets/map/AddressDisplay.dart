import 'package:Playground/services/LocationService.dart';
import 'package:flutter/material.dart';

class AddressDisplay extends StatelessWidget {

  final Address address;

  const AddressDisplay({Key key, this.address}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      address.address,
      overflow: TextOverflow.fade,
      style: TextStyle(
        color: Colors.grey[700],
        fontSize: 14
      ),
    );
  }


}