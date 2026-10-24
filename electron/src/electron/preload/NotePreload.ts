// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { contextBridge, ipcRenderer, type IpcRendererEvent } from "electron";
import type { IQuickNoteAPI } from "../../render/render.d";
import * as E from "../constants/ipcevents";

// listen to a channel the main process sends on, returns the unsubscribe function
const subscribe = <T>(channel: string, callback: (value: T) => void) => {
  const listener = (_: IpcRendererEvent, value: T) => callback(value);
  ipcRenderer.on(channel, listener);
  return () => { ipcRenderer.removeListener(channel, listener); };
};

const quickNoteAPI: IQuickNoteAPI = {
  setPinned: (pinned) => ipcRenderer.invoke(E.PIN_IN_MAIN_CHAN, pinned),
  getPinned: () => ipcRenderer.invoke(E.PINNED_IN_MAIN_CHAN),
  isMaximized: () => ipcRenderer.invoke(E.MAXIMIZED_IN_MAIN_CHAN),
  onMaximizedChange: (callback) => subscribe(E.MAXIMIZE_CHANGE_CHAN, callback),
  sendNote: (note) => ipcRenderer.invoke(E.RECV_IN_MAIN_CHAN, note),
  recvNote: () => ipcRenderer.invoke(E.SEND_IN_MAIN_CHAN),
  onError: (err) => ipcRenderer.send(E.ONER_IN_MAIN_CHAN, err),
};

// expose the QuickNoteAPI
contextBridge.exposeInMainWorld(E.QUICKNOTE_API_VAL, quickNoteAPI);
