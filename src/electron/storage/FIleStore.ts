// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import INoteStore from '../interface/INoteStore';
import asyncFile from 'node:fs/promises';
import file from 'node:fs';

/**
 * FileStore That stores notes in a file
 */
export default class FileStore implements INoteStore {
  /**
   * Gets the note from the file
   *
   * @returns Note from the file
   */
  public async getNote(): Promise<string> {
    return await asyncFile.readFile(this.file_path, 'utf-8');
  }

  /**
   * Stores the note in a file
   *
   * @param note Note to be stored
   */
  public async setNote(note: string): Promise<void> {
    return await asyncFile.writeFile(this.file_path, note);
  }

  /**
   * Constructor for FileStore
   * @param file File to store the note
   */
  public constructor(name: string) {
    file.openSync(this.file_path = name, 'a+');
  }

  /*********************************
   *  private instance variables   *
   ********************************/

  // file handle to the file to store the note
  private file_path: string;
}
