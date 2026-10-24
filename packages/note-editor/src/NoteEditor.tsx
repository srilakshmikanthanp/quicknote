// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { ContentEditable } from "@lexical/react/LexicalContentEditable";
import { LexicalErrorBoundary } from "@lexical/react/LexicalErrorBoundary";
import { LexicalComposer } from "@lexical/react/LexicalComposer";
import { RichTextPlugin } from "@lexical/react/LexicalRichTextPlugin";
import { HistoryPlugin } from "@lexical/react/LexicalHistoryPlugin";
import { AutoFocusPlugin } from "@lexical/react/LexicalAutoFocusPlugin";
import { OnChangePlugin } from '@lexical/react/LexicalOnChangePlugin';
import { ListPlugin } from "@lexical/react/LexicalListPlugin";
import { CheckListPlugin } from "@lexical/react/LexicalCheckListPlugin";
import { TabIndentationPlugin } from "@lexical/react/LexicalTabIndentationPlugin";
import { MarkdownShortcutPlugin } from "@lexical/react/LexicalMarkdownShortcutPlugin";
import { AutoLinkPlugin } from "@lexical/react/LexicalAutoLinkPlugin";
import { AutoLinkNode, autoLinkEmailMatcher, autoLinkUrlMatcher } from "@lexical/link";
import { ListItemNode, ListNode } from "@lexical/list";
import { CHECK_LIST, ORDERED_LIST, UNORDERED_LIST } from "@lexical/markdown";
import styled from 'styled-components';
import type { EditorState } from "lexical";
import LinkOpenPlugin from "./LinkOpenPlugin";
import styles from "./NoteEditor.module.css"

// NoteEditor Props Interface Definition
interface INoteEditorProps {
  initialContent: string | null;
  placeHolder: string;
  onUpdate?: (content: string) => void;
  onError: (content: Error) => void;
  autoFocus?: boolean;
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
  link: `${styles.editorLink}`,
  list: {
    ul: `${styles.editorList}`,
    ol: `${styles.editorList} ${styles.editorOrderedList}`,
    nested: { listitem: `${styles.editorNestedItem}` },
    listitem: `${styles.editorListItem}`,
    listitemChecked: `${styles.editorCheckItem} ${styles.editorChecked}`,
    listitemUnchecked: `${styles.editorCheckItem}`,
  },
  text: {
    underline: `${styles.editorTextUnderline}`,
    bold: `${styles.editorTextBold}`,
    italic: `${styles.editorTextItalic}`,
  },
}

const markdownTransformers = [CHECK_LIST, UNORDERED_LIST, ORDERED_LIST];
const editorNodes = [ListNode, ListItemNode, AutoLinkNode];
const linkMatchers = [autoLinkUrlMatcher, autoLinkEmailMatcher];

// Note Editor
export default function NoteEditor({
  initialContent,
  onUpdate,
  placeHolder,
  onError,
  autoFocus = true,
}: INoteEditorProps) {
  // editor config
  const editorConfig = (initialContent != null && initialContent != '') ? {
    editorState: initialContent,
    onError: onError,
    theme: editorTheme,
    nodes: editorNodes,
    namespace: "note-editor",
  } : {
    onError: onError,
    theme: editorTheme,
    nodes: editorNodes,
    namespace: "note-editor",
  };

  // change handler for note editor
  const onChange = (state: EditorState) => {
    onUpdate?.(JSON.stringify(state.toJSON()));
  }

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
          {autoFocus && <AutoFocusPlugin />}
          <ListPlugin />
          <CheckListPlugin />
          <TabIndentationPlugin />
          <MarkdownShortcutPlugin transformers={markdownTransformers} />
          <AutoLinkPlugin matchers={linkMatchers} />
          <LinkOpenPlugin />
        </EditorContainer>
      </LexicalComposer>
    </RootLevelContainer>
  );
}
