// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { ContentEditable } from "@lexical/react/LexicalContentEditable";
import LexicalErrorBoundary from "@lexical/react/LexicalErrorBoundary";
import { LexicalComposer } from "@lexical/react/LexicalComposer";
import { RichTextPlugin } from "@lexical/react/LexicalRichTextPlugin";
import { HistoryPlugin } from "@lexical/react/LexicalHistoryPlugin";
import { AutoFocusPlugin } from "@lexical/react/LexicalAutoFocusPlugin";
import { OnChangePlugin } from '@lexical/react/LexicalOnChangePlugin';
import styled from 'styled-components';
import { EditorState } from "lexical";

import styles from "./NoteEditor.module.css"

// NoteEditor Props
interface INoteEditorProps {
  noteContent: string;
  placeHolder: string;
  onUpdate?: (content: string) => void;
}

// top level container
// For Scrolling
const RootLevelContainer = styled.div`
  overflow: auto;
  height: 100%;
  width: 100%;
`;

// Editor Container
const EditorContainer = styled.div`
  border-radius: 2px;
  min-height: calc(100% - 20px); // 10px padding on top and bottom
  background: #fff;
  color: #000;
  padding: 10px;
  position: relative;
  overflow: auto;
`;

// Placeholder
const Placeholder = styled.div`
  text-overflow: ellipsis;
  color: #999;
  left: 10px; // same as padding in EditorContainer
  top: 10px;  // same as padding in EditorContainer
  user-select: none;
  display: inline-block;
  pointer-events: none;
  position: absolute;
`;

// Editable
const Editable = styled(ContentEditable)`
  min-height: 100px;
  resize: none;
  outline: 0;
  caret-color: #444;
`;

// editor theme
const editorTheme = {
  paragraph: `${styles.editorParagraph}`,
}

// Note Editor
export default function NoteEditor({
  noteContent,
  onUpdate,
  placeHolder,
}: INoteEditorProps) {
  // change handler for note editor
  const handleChange = (state: EditorState) => {
    onUpdate && onUpdate(JSON.stringify(state.toJSON()));
  }

  // handle the error for note editor
  const handleError = (error: Error) => {
    console.error(error);
  }

  // editor config
  const editorConfig = {
    initialState: noteContent,
    onError: handleError,
    theme: editorTheme,
    namespace: "note-editor",
  };

  // placeholder
  const placeholder = (
    <Placeholder>
      {placeHolder}
    </Placeholder>
  );

  // content
  console.log("Content:", noteContent);

  // render
  return (
    <RootLevelContainer>
      <LexicalComposer initialConfig={editorConfig}>
        <EditorContainer>
          <RichTextPlugin
            ErrorBoundary={LexicalErrorBoundary}
            placeholder={placeholder}
            contentEditable={<Editable />}
          />
          <HistoryPlugin />
          <AutoFocusPlugin />
          <OnChangePlugin onChange={handleChange} />
        </EditorContainer>
      </LexicalComposer>
    </RootLevelContainer>
  );
}
