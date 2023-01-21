// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { contextBridge, ipcRenderer } from "electron";
import * as C from "../constants";

// expose the QuickNoteAPI
contextBridge.exposeInMainWorld(C.QUICKNOTE_API, {
  sendNote: (note: string) => ipcRenderer.send(C.RECV_FROM_RENDER, note),
  recvNote: () => ipcRenderer.invoke(C.SEND_NOTE_RENDER),
});
