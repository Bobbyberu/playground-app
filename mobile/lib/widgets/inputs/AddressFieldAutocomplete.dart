import 'package:Playground/services/LocationService.dart';
import 'package:Playground/widgets/style/PlaygorundTextFieldStyle.dart';
import 'package:flutter/material.dart';

class AddressFieldAutocomplete extends StatefulWidget {

  Address value;

  final Function(Address) onTap;
  final TextEditingController controller;

  AddressFieldAutocomplete({Key key, this.onTap, this.controller}) : super(key: key);

  @override
  State<StatefulWidget> createState() => AddressFieldAutocompleteState();

}

class AddressFieldAutocompleteState extends State<AddressFieldAutocomplete> {

  List<Address> predictions;
  bool _isSearching;

  final FocusNode _focusNode = FocusNode();
  OverlayEntry _overlayEntry;
  final LayerLink _layerLink = LayerLink();

  @override
  void initState() {
    super.initState();
    _isSearching = false;
    predictions = new List();

    _focusNode.addListener(() {
      triggerOverlay();
    });
  }

  void triggerOverlay() {
    if (_focusNode.hasFocus) {

      this._overlayEntry = this._createOverlayEntry();
      Overlay.of(context).insert(this._overlayEntry);

    } else {
      this._overlayEntry.remove();
    }
  }

  void loadPredictions(String value) async {
    if(_isSearching) {
      return;
    }
    setState(() {
      _isSearching = true;
    });
    LocationService.getPredictionsOfAddress(value).then((response) {
      predictions = response;
      _isSearching = false;
      triggerOverlay();
    });
  }

  OverlayEntry _createOverlayEntry() {
    RenderBox renderBox = context.findRenderObject();
    var size = renderBox.size;

    return OverlayEntry(
        builder: (context) => Positioned(
          width: size.width,
          child: CompositedTransformFollower(
            link: this._layerLink,
            showWhenUnlinked: false,
            offset: Offset(0.0, size.height),
            child: Material(
              elevation: 1.0,
              child: ListView(
                padding: EdgeInsets.zero,
                shrinkWrap: true,
                children: predictions.map((a) => ListTile(
                  title: Text(a.address, maxLines: 1, overflow: TextOverflow.ellipsis),
                  onTap: () {
                    widget.onTap(a);
                    predictions.clear();
                    _focusNode.unfocus();
                  },
                )).toList()
              ),
            ),
          ),
        )
    );
  }

  @override
  Widget build(BuildContext context) {

    return CompositedTransformTarget(
      link: this._layerLink,
      child: TextField(
        controller: widget.controller,
        focusNode: this._focusNode,
        onChanged: (value) {
          _address = value;
          loadPredictions(value);
        },
        style: PlaygroundTextFieldStyle.getStyle(context),
        decoration: PlaygroundTextFieldStyle.getDecoration(context, "24 Rue du plaisir"),
      )
    );

  }

}