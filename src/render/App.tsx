// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import React from "react";
import { NoteEditor } from "./components";
import * as C from "./constants";

/**
 * The main application component.
 */
export default function App() {
  // Get the note from the store.
  const [initialContent, setInitialContent] = React.useState<string>(null);

  // Get the note from the store.
  React.useEffect(() => {
    window.QuickNoteAPI.recvNote().then(setInitialContent).catch(onError);
    console.log(initialContent);
  }, []);

  // Set the note in the store.
  const saveNote = (note: string) => {
    initialContent != null && window.QuickNoteAPI.sendNote(note).catch(onError);
  };

  // on error
  const onError = (err: Error) => {
    window.QuickNoteAPI.onError(err.message);
  };

  // render the App
  return (
    <NoteEditor
      placeHolder={C.PLACEHOLDER_TEXT}
      onUpdate={saveNote}
      initialContent={initialContent}
      onError={onError}
      key={initialContent}
    />
  );
}
