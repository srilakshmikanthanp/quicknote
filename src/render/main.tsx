// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import ReactDOM from "react-dom/client";
import "./styles/main.plain.css";
import App from "./App";

// root element
const rootElement = document.getElementById("root");

// react root
const reactRoot = ReactDOM.createRoot(rootElement);

// render the app
reactRoot.render(
  <App />
);
