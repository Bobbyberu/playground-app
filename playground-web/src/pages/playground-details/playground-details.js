import React, {Component} from 'react';
import img from '../../pictures/playground.jpg';
import './playground-details.css';

const PlaygroundDetails = ({ name, adress }) => (
    <div className="global">
        <div className="header">
            <h1>{name}</h1>
            <img src={img}/>
        </div>
        <div className="leftSide">
            <span className="label location">Location : {adress}</span>
        </div>
        <div className="rightSide">

        </div>
    </div>
)

export default PlaygroundDetails