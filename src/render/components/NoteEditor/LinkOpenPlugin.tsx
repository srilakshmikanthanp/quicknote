// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { useLexicalComposerContext } from "@lexical/react/LexicalComposerContext";
import { useEffect } from "react";

/**
 * Opens a link on Ctrl/Cmd + click. A plain click only places the
 * caret, so links stay editable and the window never navigates away.
 */
export default function LinkOpenPlugin() {
  const [editor] = useLexicalComposerContext();

  useEffect(() => {
    return editor.registerRootListener((root, prevRoot) => {
      const onClick = (event: MouseEvent) => {
        const anchor = (event.target as HTMLElement).closest("a");
        if (!anchor) return;
        event.preventDefault();
        if (event.ctrlKey || event.metaKey) window.open(anchor.href);
      };

      prevRoot?.removeEventListener("click", onClick);
      root?.addEventListener("click", onClick);
    });
  }, [editor]);

  return null;
}
