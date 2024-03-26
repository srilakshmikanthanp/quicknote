// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { SetNote } from "./redux/slices";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import store from "./redux/store";
import "./styles/main.plain.css";
import App from "./App";

// Function to be called on note change
function onNoteChange() {
  window.QuickNoteAPI.sendNote(store.getState().quicknote.note);
}

// set up the state of quick note
window.QuickNoteAPI.recvNote().then((note) => {
  store.dispatch(SetNote(note));
}).then(() => {
  store.subscribe(onNoteChange);
});

// root element
const rootElement = document.getElementById("root");

// react root
const reactRoot = ReactDOM.createRoot(rootElement);

// render the app
reactRoot.render(
  <Provider store={store}>
    <App />
  </Provider>
);
