// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import settings from 'electron-settings';

// key for the note window size
export const WINDOW_SIZE = 'quicknote-window-size';

/**
 * Used To get the window width and height
 * 
 * @returns default [width, height] of the window
 */
export const getWindowSize = async (
  [width, height]: [number, number]
): Promise<[number, number]> => {
  return !await settings.has(WINDOW_SIZE) ? [width, height]
    : await settings.get(WINDOW_SIZE) as [number, number];
}

/**
 * Used To set the window Width and Height
 * 
 * @param [ width, height ] of the window to set
 */
export const setWindowSize = async (
  [width, height]: [number, number]
): Promise<void> => {
  return await settings.set(WINDOW_SIZE, [width, height]);
}
