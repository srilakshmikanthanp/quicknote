// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import React from "react";
import { NoteEditor } from "@quicknote/editor";
import logo from "../assets/images/quicknote-64.png";
import * as C from "./constants";

/**
 * The main application component.
 */
export default function App() {
  const [initialContent, setInitialContent] = React.useState<string | null>(null);

  React.useEffect(() => {
    window.QuickNoteAPI.recvNote().then(setInitialContent).catch(onError);
    console.log(initialContent);
    window.QuickNoteAPI.getPinned().then(setPinned).catch(onError);
  }, []);

  const [pinned, setPinned] = React.useState(false);

  const togglePin = () => {
    window.QuickNoteAPI.setPinned(!pinned).then(setPinned).catch(onError);
  };

  const saveNote = (note: string) => {
    initialContent != null && window.QuickNoteAPI.sendNote(note).catch(onError);
  };

  const onError = (err: Error) => {
    window.QuickNoteAPI.onError(err.message);
  };

  return (
    <NoteEditor
      placeHolder={C.PLACEHOLDER_TEXT}
      onUpdate={saveNote}
      initialContent={initialContent}
      onError={onError}
      toolbar
      titleBar
      pinned={pinned}
      draggable={pinned}
      onTogglePin={togglePin}
      onClose={() => window.QuickNoteAPI.hide()}
      icon={<img src={logo} width={18} height={18} alt="" />}
      key={initialContent}
    />
  );
}
