// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { LuPin } from "react-icons/lu";
import styles from "./TitleBar.module.css";

interface ITitleBarProps {
  pinned: boolean;
  onTogglePin: () => void;
}

export default function TitleBar({ pinned, onTogglePin }: ITitleBarProps) {
  const pinButton = (
    <button
      className={`${styles.pin} ${pinned ? styles.pinned : ""}`}
      title={pinned ? "Unpin (hide when focus is lost)" : "Pin to keep open and move"}
      aria-pressed={pinned}
      onClick={onTogglePin}
    >
      <LuPin size={14} fill={pinned ? "currentColor" : "none"} />
    </button>
  );

  if (!pinned) {
    return <div className={styles.corner}>{pinButton}</div>;
  }

  return (
    <div className={styles.titleBar}>
      <span className={styles.grip} aria-hidden>•••</span>
      {pinButton}
    </div>
  );
}
