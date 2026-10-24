// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import React from "react";
import { NoteEditor, TitleBar } from "./components";
import * as C from "./constants";

/**
 * The main application component.
 */
export default function App() {
  // Get the note from the store.
  const [initialContent, setInitialContent] = React.useState<string | null>(null);

  // Get the note from the store.
  React.useEffect(() => {
    window.QuickNoteAPI.recvNote().then(setInitialContent).catch(onError);
    console.log(initialContent);
    window.QuickNoteAPI.getPinned().then(setPinned).catch(onError);
  }, []);

  const [pinned, setPinned] = React.useState(false);

  const togglePin = () => {
    window.QuickNoteAPI.setPinned(!pinned).then(setPinned).catch(onError);
  };

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
    <div style={{ display: "flex", flexDirection: "column", height: "100%", position: "relative" }}>
      <TitleBar pinned={pinned} onTogglePin={togglePin} />
      <div style={{ flex: 1, minHeight: 0 }}>
        <NoteEditor
          placeHolder={C.PLACEHOLDER_TEXT}
          onUpdate={saveNote}
          initialContent={initialContent}
          onError={onError}
          key={initialContent}
        />
      </div>
    </div>
  );
}
