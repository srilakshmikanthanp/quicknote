// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

export interface IQuickNoteAPI {
  sendNote(note: string | null): Promise<void>;
  recvNote(): Promise<string>;
  onError(err: string): Promise<void>;
}

declare global {
  interface Window {
    QuickNoteAPI: IQuickNoteAPI;
  }
}
