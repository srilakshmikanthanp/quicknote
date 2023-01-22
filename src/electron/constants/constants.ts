// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import path from "path";
import os from "os";

// Application Icon
export const APPLICATION_ICON   =   path.join(__dirname, "../../assets/images/quicknote.png");

// Application Home
export const APPLICATION_HOME   =   path.join(os.homedir(), ".quicknote");

// Application Name
export const APPLICATION_NAME   =   "QuickNote";

// Application URL
export const APPLICATION_URL    =   "https://github.com/srilakshmikanthanp/quicknote";

// Application Issue URL
export const ISSUE_RAISE_URL    =   "https://github.com/srilakshmikanthanp/quicknote/issues/new";

// Application Size
export const APPLICATION_SIZE   =   [350, 250] as [number, number];
