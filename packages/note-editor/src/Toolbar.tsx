// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { useLexicalComposerContext } from "@lexical/react/LexicalComposerContext";
import { CAN_REDO_COMMAND, CAN_UNDO_COMMAND, COMMAND_PRIORITY_LOW, REDO_COMMAND, UNDO_COMMAND } from "lexical";
import { useEffect, useState } from "react";
import { LuRedo2, LuUndo2 } from "react-icons/lu";
import styles from "./Toolbar.module.css";

export default function Toolbar() {
  const [editor] = useLexicalComposerContext();
  const [canUndo, setCanUndo] = useState(false);
  const [canRedo, setCanRedo] = useState(false);

  useEffect(() => {
    const stopUndo = editor.registerCommand(CAN_UNDO_COMMAND, (can) => { setCanUndo(can); return false; }, COMMAND_PRIORITY_LOW);
    const stopRedo = editor.registerCommand(CAN_REDO_COMMAND, (can) => { setCanRedo(can); return false; }, COMMAND_PRIORITY_LOW);

    return () => { stopUndo(); stopRedo(); };
  }, [editor]);

  // keep the caret in the note when a button is pressed
  const keepFocus = (event: React.MouseEvent) => event.preventDefault();

  return (
    <div className={styles.toolbar}>
      <button className={styles.button} title="Undo" disabled={!canUndo} onMouseDown={keepFocus}
        onClick={() => editor.dispatchCommand(UNDO_COMMAND, undefined)}>
        <LuUndo2 size={16} />
      </button>
      <button className={styles.button} title="Redo" disabled={!canRedo} onMouseDown={keepFocus}
        onClick={() => editor.dispatchCommand(REDO_COMMAND, undefined)}>
        <LuRedo2 size={16} />
      </button>
    </div>
  );
}
