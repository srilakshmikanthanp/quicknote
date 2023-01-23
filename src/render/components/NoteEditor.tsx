// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import 'react-quill/dist/quill.bubble.css';
import ReactQuill from 'react-quill';
import styled from 'styled-components';

// NoteEditor Props
interface INoteEditorProps {
  noteContent: string;
  placeHolder: string;
  onUpdate?: (content: string) => void;
}

// Editor Container
const EditorContainer = styled.div`
  background-color: var(--editor-bg-color);
  color: var(--editor-fg-color);
  min-height: 100%;

  .ql-editor.ql-blank::before {
    color: gray;
  }
`;

// Note Editor
export default function NoteEditor({
  noteContent,
  onUpdate,
  placeHolder,
}: INoteEditorProps) {
  // change handler for note editor
  const handleChange = (content: string) => {
    onUpdate && onUpdate(content);
  }

  // render
  return (
    <EditorContainer>
      <ReactQuill
        onChange={handleChange}
        value={noteContent}
        placeholder={placeHolder}
        theme="bubble"
      />
    </EditorContainer>
  );
}
