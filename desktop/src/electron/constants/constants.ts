// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import path from "path";
import os from "os";
import { app } from "electron";

export const APPLICATION_ICON     = app.isPackaged ? path.join(process.resourcesPath, "quicknote.png") : path.join(__dirname, "../../src/assets/images/quicknote.png");

export const APPLICATION_HOME     = path.join(os.homedir(), ".quicknote");

export const APPLICATION_NAME     = "QuickNote";

export const APPLICATION_URL      = "https://github.com/srilakshmikanthanp/quicknote";

export const ISSUE_RAISE_URL      = "https://github.com/srilakshmikanthanp/quicknote/issues/new";

export const APP_DONATE_URL       = "https://srilakshmikanthanp.github.io/donate/"

export const APPLICATION_SIZE     = [439, 336] as [number, number];

export const SHORTCUT_KEY         = 'QuicknoteShortcutKey';

export const WINDOW_SIZE          = 'QuicknoteWindowSize';

export const THEME_SOURCE         = 'QuicknoteThemeSource';

export const DEFAULT_SHORTCUT_KEY = 'CommandOrControl+Alt+Q';
