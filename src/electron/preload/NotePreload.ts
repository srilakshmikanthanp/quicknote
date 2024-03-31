// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { contextBridge, ipcRenderer } from "electron";
import * as E from "../constants/ipcevents";

// expose the QuickNoteAPI
contextBridge.exposeInMainWorld(E.QUICKNOTE_API_VAL, {
  sendNote: (note: string) => ipcRenderer.invoke(E.RECV_IN_MAIN_CHAN, note),
  recvNote: () => ipcRenderer.invoke(E.SEND_IN_MAIN_CHAN),
  onError: (err: string) => ipcRenderer.send(E.ONER_IN_MAIN_CHAN, err),
});
