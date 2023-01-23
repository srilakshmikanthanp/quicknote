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

// observe the note in the store and send it to QuickNoteAPI
store.subscribe(() => {
  window.QuickNoteAPI.sendNote(store.getState().quicknote.note);
});

// load the initial note from QuickNoteAPI
window.QuickNoteAPI.recvNote().then((note) => {
  store.dispatch(SetNote(note));
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
