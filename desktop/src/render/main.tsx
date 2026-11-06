// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import ReactDOM from "react-dom/client";
import "./styles/main.plain.css";
import App from "./App";

const rootElement = document.getElementById("root");

if (!rootElement) {
  throw new Error("Root element not found");
}

const reactRoot = ReactDOM.createRoot(rootElement);

reactRoot.render(
  <App />
);
