// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import styled from "styled-components";
import { ChangeEvent } from "react";

interface NoteEditorProps {
  placeholder: string;
  content: string;
  onChange?: (content: string) => void;
  onError?: (error: Error) => void;
}

const EditorTheme = styled.textarea`
  border-radius: 5px;
`;

export default function NoteEditor(props: NoteEditorProps) {
  const handleEditorChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    props.onChange?.(e.target.value);
  };

  return (
    <EditorTheme
      placeholder={props.placeholder}
      value={props.content}
      onChange={handleEditorChange}
    />
  );
}
