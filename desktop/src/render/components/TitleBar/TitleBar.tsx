// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import type { ReactNode } from "react";
import { LuPin, LuX } from "react-icons/lu";
import styles from "./TitleBar.module.css";

interface ITitleBarProps {
  pinned: boolean;
  onTogglePin: () => void;
  onClose?: () => void;
  title?: string;
  icon?: ReactNode;
}

export default function TitleBar({ pinned, onTogglePin, onClose, title, icon }: ITitleBarProps) {
  return (
    <div className={`${styles.titleBar} ${pinned ? styles.draggable : ""}`}>
      {(icon || title) && <span className={styles.title}>{icon}{title}</span>}
      {pinned && <span className={styles.grip} aria-hidden>•••</span>}
      <button
        className={`${styles.pin} ${pinned ? styles.pinned : ""}`}
        title={pinned ? "Unpin (hide when focus is lost)" : "Pin to keep open, move and resize"}
        aria-pressed={pinned}
        onClick={onTogglePin}
      >
        <LuPin size={16} fill={pinned ? "currentColor" : "none"} />
      </button>
      {onClose && (
        <button className={styles.pin} title="Close" onClick={onClose}>
          <LuX size={16} />
        </button>
      )}
    </div>
  );
}
