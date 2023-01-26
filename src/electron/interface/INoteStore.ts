// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

/**
 * @interface NoteStore used to store the note
 */
export default interface INoteStore {
  /**
   * Stores the note in a storage medium
   * @param note Note to be stored
   */
  setNote(note: string): Promise<void> | void;

  /**
   * Gets the note from the storage medium
   * @returns Note from the storage medium
   */
  getNote(): Promise<string> | string;
}
