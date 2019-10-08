import React, { Component } from "react";
import AppRouter from "./AppRouter";
import "./App.css";
import { Provider } from "react-redux";
import Store from "./store/configureStore";

class App extends Component {
  render() {
    return (
      <Provider store={Store}>
        <div className="App">
          <AppRouter />
          <link
            rel="stylesheet"
            href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css"
            integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
            crossOrigin=""
          />
        </div>
      </Provider>
    );
  }
}

export default App;
