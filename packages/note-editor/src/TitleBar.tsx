// Copyright (c) 2026 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import type { ReactNode } from "react";
import { LuPin, LuX } from "react-icons/lu";
import styled, { css } from "styled-components";

interface ITitleBarProps {
  pinned: boolean;
  draggable?: boolean;
  onTogglePin?: () => void;
  onClose?: () => void;
  icon?: ReactNode;
}

const Bar = styled.div<{ $draggable: boolean }>`
  display: flex;
  flex: none;
  align-items: center;
  justify-content: flex-end;
  height: 36px;
  padding: 0 8px 0 12px;
  background: var(--bar-bg);
  color: var(--bar-fg);
  border-bottom: 1px solid var(--bar-divider);
  user-select: none;

  & > button:first-of-type {
    margin-left: auto;
  }

  ${({ $draggable }) => $draggable && css`
    -webkit-app-region: drag;
    cursor: grab;
  `}
`;

const Icon = styled.span`
  display: flex;
  align-items: center;
`;

const Grip = styled.span`
  flex: 1;
  text-align: center;
  letter-spacing: 4px;
  font-size: 10px;
  opacity: 0.45;
`;

const IconButton = styled.button<{ $active?: boolean }>`
  display: grid;
  place-items: center;
  width: 26px;
  height: 26px;
  padding: 0;
  border: 0;
  border-radius: 6px;
  background: transparent;
  color: ${({ $active }) => ($active ? "var(--editor-accent-color)" : "inherit")};
  cursor: pointer;
  transition: background 0.15s, color 0.15s;
  -webkit-app-region: no-drag;

  &:hover {
    background: color-mix(in srgb, currentColor 14%, transparent);
  }
`;

export default function TitleBar({ pinned, draggable = false, onTogglePin, onClose, icon }: ITitleBarProps) {
  return (
    <Bar $draggable={draggable}>
      {icon && <Icon>{icon}</Icon>}
      {draggable && <Grip aria-hidden>•••</Grip>}
      <IconButton
        $active={pinned}
        title={pinned ? "Unpin (hide when focus is lost)" : "Pin to keep open, move and resize"}
        aria-pressed={pinned}
        onClick={onTogglePin}
      >
        <LuPin size={16} fill={pinned ? "currentColor" : "none"} />
      </IconButton>
      {onClose && (
        <IconButton title="Close" onClick={onClose}>
          <LuX size={16} />
        </IconButton>
      )}
    </Bar>
  );
}
