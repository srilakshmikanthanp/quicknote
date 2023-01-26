// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import * as C from '../constants/constants';
import { nativeTheme } from 'electron';

/**
 * Get Application Icon based on the theme
 * 
 * @returns Application Icon
 */
export function getApplicationIcon() {
  return nativeTheme.shouldUseDarkColors ? C.QUICKNOTE_LIGHT : C.QUICKNOTE_DARK;
}
