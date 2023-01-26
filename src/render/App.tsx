// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { useDispatch, useSelector } from "react-redux";
import { SetNote, selectNote } from "./redux/slices";
import { NoteEditor } from "./components";
import * as C from "./constants";

/**
 * The main application component.
 */
export default function App() {
  // Get the note from the store.
  const noteContent = useSelector(selectNote);

  // Get the dispatch function.
  const dispatch = useDispatch();

  // Set the note in the store.
  const setNote = (note: string) => {
    dispatch(SetNote(note));
  };

  // render the App
  return (
    <NoteEditor
      placeHolder={C.PLACEHOLDER_TEXT}
      onUpdate={setNote}
      noteContent={noteContent}
    />
  );
}
