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
   * get the new File Store instance
   * @param path Path to the file
   * 
   * @returns FileStore instance
   */
  public static async getStore(path: string): Promise<FileStore> {
    return new FileStore(await file.open(path, 'a+'));
  }

  /**
   * Constructor for FileStore
   * @param file File to store the note
   */
  private constructor(file: file.FileHandle) {
    this.file_handle = file;
  }

  /**
   * Stores the note in a file
   * 
   * @param note Note to be stored
   */
  public async setNote(note: string): Promise<void> {
    return await this.file_handle.writeFile(note);
  }

  /**
   * Gets the note from the file
   * 
   * @returns Note from the file
   */
  public getNote(): Promise<string> {
    return this.file_handle.readFile().then((data) => {
      return data.toString()
    });
  }

  /*********************************
   *  private instance variables   *
   ********************************/

  // file handle to the file to store the note
  private file_handle: file.FileHandle;
}
