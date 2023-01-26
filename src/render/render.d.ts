// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

export interface IQuickNoteAPI {
  sendNote(note: string): Promise<void>;
  recvNote(): Promise<string>;
}

declare global {
  interface Window {
    QuickNoteAPI: IQuickNoteAPI;
  }
}
