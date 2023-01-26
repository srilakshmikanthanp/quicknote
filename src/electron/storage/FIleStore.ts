// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import INoteStore from '../interface/INoteStore';
import file from 'node:fs/promises';

/**
 * FileStore That stores notes in a file
 */
export default class FileStore implements INoteStore {

  /**
   * Constructor for FileStore
   * @param file File to store the note
   */
  public constructor(file: string) {
    this.file_path = file;
  }

  /**
   * Stores the note in a file
   * 
   * @param note Note to be stored
   */
  public async setNote(note: string): Promise<void> {
    return await file.writeFile(this.file_path, note);
  }

  /**
   * Gets the note from the file
   * 
   * @returns Note from the file
   */
  public async getNote(): Promise<string> {
    return await file.readFile(this.file_path, 'utf-8');
  }

  /*********************************
   *  private instance variables   *
   ********************************/

  // file handle to the file to store the note
  private file_path: string;
}
