// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

/**
 * @interface NoteStore used to store the note
 */
export default interface NoteStore {
  setNote(note: string): void
  getNote(): string
}
