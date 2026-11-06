// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { useLexicalComposerContext } from "@lexical/react/LexicalComposerContext";
import { CAN_REDO_COMMAND, CAN_UNDO_COMMAND, COMMAND_PRIORITY_LOW, REDO_COMMAND, UNDO_COMMAND } from "lexical";
import { useEffect, useState } from "react";
import { LuRedo2, LuUndo2 } from "react-icons/lu";
import styled from "styled-components";

const Bar = styled.div`
  display: flex;
  flex: none;
  align-items: center;
  gap: 2px;
  height: 32px;
  padding: 0 10px;
  background: var(--bar-bg, #f1f3f6);
  color: var(--bar-fg, #4b5563);
  border-top: 1px solid var(--bar-divider, #d9dde3);
  user-select: none;
`;

const Button = styled.button`
  display: grid;
  place-items: center;
  width: 26px;
  height: 24px;
  padding: 0;
  border: 0;
  border-radius: 4px;
  background: transparent;
  color: inherit;
  cursor: pointer;

  &:hover:not(:disabled) {
    background: color-mix(in srgb, currentColor 14%, transparent);
  }

  &:disabled {
    opacity: 0.35;
    cursor: default;
  }
`;

export default function Toolbar() {
  const [editor] = useLexicalComposerContext();
  const [canUndo, setCanUndo] = useState(false);
  const [canRedo, setCanRedo] = useState(false);

  useEffect(() => {
    const stopUndo = editor.registerCommand(CAN_UNDO_COMMAND, (can) => { setCanUndo(can); return false; }, COMMAND_PRIORITY_LOW);
    const stopRedo = editor.registerCommand(CAN_REDO_COMMAND, (can) => { setCanRedo(can); return false; }, COMMAND_PRIORITY_LOW);

    return () => { stopUndo(); stopRedo(); };
  }, [editor]);

  const keepFocus = (event: React.MouseEvent) => event.preventDefault();

  return (
    <Bar>
      <Button title="Undo" disabled={!canUndo} onMouseDown={keepFocus}
        onClick={() => editor.dispatchCommand(UNDO_COMMAND, undefined)}>
        <LuUndo2 size={16} />
      </Button>
      <Button title="Redo" disabled={!canRedo} onMouseDown={keepFocus}
        onClick={() => editor.dispatchCommand(REDO_COMMAND, undefined)}>
        <LuRedo2 size={16} />
      </Button>
    </Bar>
  );
}
