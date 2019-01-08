import React from 'react';

// On crée un composant emoji accessible et réutilisable
const Emoji = props => (
    <span
        className="emoji"
        role="img"
        aria-label={props.label ? props.label : ""}
        aria-hidden={props.label ? "false" : "true"}
    >
        {props.symbol}
    </span>

);

export default Emoji;