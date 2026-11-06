// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { contextBridge, ipcRenderer } from "electron";
import type { IQuickNoteAPI } from "../../render/render.d";
import * as E from "../constants/ipcevents";

const quickNoteAPI: IQuickNoteAPI = {
  setPinned: (pinned) => ipcRenderer.invoke(E.PIN_IN_MAIN_CHAN, pinned),
  hide: () => ipcRenderer.send(E.HIDE_IN_MAIN_CHAN),
  getPinned: () => ipcRenderer.invoke(E.PINNED_IN_MAIN_CHAN),
  sendNote: (note) => ipcRenderer.invoke(E.RECV_IN_MAIN_CHAN, note),
  recvNote: () => ipcRenderer.invoke(E.SEND_IN_MAIN_CHAN),
  onError: (err) => ipcRenderer.send(E.ONER_IN_MAIN_CHAN, err),
};

contextBridge.exposeInMainWorld(E.QUICKNOTE_API_VAL, quickNoteAPI);
