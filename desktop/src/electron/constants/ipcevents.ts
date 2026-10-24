// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

// exposed API NAME
export const QUICKNOTE_API_VAL = "QuickNoteAPI";

// IPC note send channel
export const SEND_IN_MAIN_CHAN = "ipc:note:send";

// IPC note receive channel
export const RECV_IN_MAIN_CHAN = "ipc:note:recv";

// IPC to receive Error
export const ONER_IN_MAIN_CHAN = "ipc:evt:error";

// IPC to pin the window on top
export const PIN_IN_MAIN_CHAN = "ipc:win:pin";

// IPC to get the pinned state of the window
export const PINNED_IN_MAIN_CHAN = "ipc:win:pinned";

// IPC to get the maximized state of the window
export const MAXIMIZED_IN_MAIN_CHAN = "ipc:win:maximized";

// IPC sent to the renderer when the window is maximized or restored
export const MAXIMIZE_CHANGE_CHAN = "ipc:win:maximize-change";
