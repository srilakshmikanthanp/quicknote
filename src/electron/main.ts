// Copyright (c) 2022 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { Menu, MenuItem, dialog, app, ipcMain, globalShortcut } from 'electron';
import { configure } from 'electron-settings';
import squirrelStartup from 'electron-squirrel-startup';

import path from 'path';
import fs from 'fs';

import NoteWindow from './components/NoteWindow';
import NoteTray from './components/NoteTray';
import FileStore from './storage/FIleStore';
import * as settings from "./settings";
import * as C from './constants/constants';
import * as E from './constants/ipcevents';

/******************************************
 *          App startup phase             *
 *****************************************/

if (squirrelStartup) {
  app.quit();
}

if (!app.requestSingleInstanceLock()) {
  app.quit();
}

app.setLoginItemSettings({
  openAtLogin: app.isPackaged,
  path: app.getPath("exe")
});

app.commandLine.appendSwitch('wm-window-animations-disabled');

if (process.env.WAYLAND_DISPLAY) {
  app.commandLine.appendSwitch('ozone-platform', 'x11');
}

if (!fs.existsSync(C.APPLICATION_HOME)) {
  fs.mkdirSync(C.APPLICATION_HOME, { recursive: true })
}

configure({
  dir: C.APPLICATION_HOME,
  prettify: true,
  fileName: "prefs.json"
});

app.on('ready', async () => {
  const fileStore = new FileStore(path.join(C.APPLICATION_HOME, ".quicknote"));

  ipcMain.handle(E.RECV_IN_MAIN_CHAN, async (e, arg) => {
    return await fileStore.setNote(arg);
  });

  ipcMain.handle(E.SEND_IN_MAIN_CHAN, async () => {
    return await fileStore.getNote();
  });

  ipcMain.on(E.ONER_IN_MAIN_CHAN, async (e, arg) => {
    dialog.showErrorBox(C.APPLICATION_NAME, arg); app.exit();
  });

  const noteWindow = new NoteWindow({
    icon: C.APPLICATION_ICON,
    frame: false,
    show: false,
    skipTaskbar: true,
    resizable: true,
    transparent: true,
    hasShadow: true,
    alwaysOnTop: true,
    webPreferences: {
      preload: path.join(__dirname, 'NotePreload.js'),
      devTools: !app.isPackaged,
    }
  });

  noteWindow.setSize(...await settings.getWindowSize(C.APPLICATION_SIZE));

  noteWindow.on('resized', () => {
    settings.setWindowSize(noteWindow.getSize() as [number, number]);
  });

  noteWindow.on('hide', () => {
    settings.setWindowSize(noteWindow.getSize() as [number, number]);
  });

  const tray = new NoteTray(() => noteWindow.showNote());

  const menu = new Menu();

  menu.append(new MenuItem({
    label: 'Hide QuickNote',
    visible: false,
    accelerator: 'Esc',
    click: () => noteWindow.hide(),
  }));

  menu.append(new MenuItem({
    enabled: !app.isPackaged,
    label: "Open Dev Tools",
    visible: false,
    accelerator: "Control+D",
    click: () => noteWindow.webContents.openDevTools(),
  }));

  menu.append(new MenuItem({
    label: "Quit QuickNote",
    visible: false,
    accelerator: "Control+Q",
    click: () => app.quit(),
  }));

  Menu.setApplicationMenu(menu);

  app.on('quit', () => tray.destroy());

  if (MAIN_WINDOW_VITE_DEV_SERVER_URL) {
    noteWindow.loadURL(MAIN_WINDOW_VITE_DEV_SERVER_URL);
  } else {
    noteWindow.loadFile(path.join(__dirname, `../renderer/${MAIN_WINDOW_VITE_NAME}/index.html`));
  }

  const shortcutKey = await settings.getShortCutKey(C.DEFAULT_SHORTCUT_KEY);
  const registered = globalShortcut.register(shortcutKey, () => { noteWindow.showNote(); });

  if (!registered) {
    dialog.showErrorBox(C.APPLICATION_NAME, `Could not register shortcut ${shortcutKey}.\nAnother app may be using it.`);
  }
});
