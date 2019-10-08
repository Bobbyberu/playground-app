import React from "react";
import PropTypes from "prop-types";

class favouriteInfos extends React.Component {
  render() {
    return "Ces informations seront disponibles après le développement du sprint 3";
  }
}

favouriteInfos.propTypes = {
  classes: PropTypes.object.isRequired
};

export default favouriteInfos;
