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

// NoteEditor Props Interface Definition
interface INoteEditorProps {
  noteContent: string;
  placeHolder: string;
  onUpdate?: (content: string) => void;
}

// top level container For Scrolling
const RootLevelContainer = styled.div`
  overflow: auto;
  width: 100%;
  height: 100%;
`;

// Editor Container
const EditorContainer = styled.div`
  background: var(--editor-bg-color);
  color: var(--editor-fg-color);
  border-radius: 2px;
  position: relative;
  padding: 10px;        // ref [1]
  min-height: 100%;
  min-width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
`;

// Placeholder
const Placeholder = styled.div`
  text-overflow: ellipsis;
  position: absolute;
  left: 10px;           // @ref [1]
  top: 10px;            // @ref [1]
  color: #999;
  font-style: italic;
  user-select: none;
  pointer-events: none;
`;

// Editable
const Editable = styled(ContentEditable)`
  min-width: 100%;
  outline: 0;
  flex-grow: 1;
`;

// editor theme
const editorTheme = {
  paragraph: `${styles.editorParagraph}`,
}

// Note Editor
export default function NoteEditor({ 
  noteContent, 
  onUpdate, 
  placeHolder 
}: INoteEditorProps) {
  // change handler for note editor
  const onChange = (state: EditorState) => {
    onUpdate && onUpdate(JSON.stringify(state.toJSON()));
  }

  // handle the error for note editor
  const onError = (error: Error) => {
    console.error(error);
  }

  // editor config
  const editorConfig = {
    editorState: noteContent,
    onError: onError,
    theme: editorTheme,
    namespace: "note-editor",
  };

  // placeholder
  const placeholder = (
    <Placeholder>
      {placeHolder}
    </Placeholder>
  );

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
          <OnChangePlugin onChange={onChange} />
          <HistoryPlugin />
          <AutoFocusPlugin />
        </EditorContainer>
      </LexicalComposer>
    </RootLevelContainer>
  );
}
