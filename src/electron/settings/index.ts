// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { WINDOW_SIZE, SHORTCUT_KEY } from '../constants/constants';
import settings from 'electron-settings';

/**
 * Used To get the window width and height
 * 
 * @returns [width, height] of the window
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
 * @param [ width, height ] of the window
 */
export const setWindowSize = async (
  [width, height]: [number, number]
): Promise<void> => {
  return await settings.set(WINDOW_SIZE, [width, height]);
}

/**
 * Used To get the ShortKey of App
 */
export const getShortCutKey = async (
  key: string
): Promise<string> => {
  return !await settings.has(SHORTCUT_KEY) ? key
    : await settings.get(SHORTCUT_KEY) as string;
}

/**
 * Used to set the ShortKey of App
 */
export const setShortCutKey = async (
  key: string
): Promise<void> => {
  return await settings.set(SHORTCUT_KEY, key);
}