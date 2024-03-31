// Copyright (c) 2022 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT


import { Menu, MenuItem, dialog, app, ipcMain, globalShortcut, screen } from 'electron';
import { configure } from 'electron-settings';

import open from 'open';
import path from 'path';
import fs from 'fs';

import TrayWindow from './components/TrayWindow';
import FileStore from './storage/FIleStore';
import * as settings from "./settings";
import * as C from './constants/constants';
import * as E from './constants/ipcevents';


// This allows TypeScript to pick up the magic constants that's auto-generated by Forge's Webpack
// plugin that tells the Electron app where to look for the Webpack-bundled app code (depending on
// whether you're running in development or production).


// Main Window Preload Entry
declare const NOTE_WINDOW_PRELOAD_WEBPACK_ENTRY: string;

// Main Window Load entry
declare const NOTE_WINDOW_WEBPACK_ENTRY: string;


/******************************************
 *          App startup phase             *
 *****************************************/

// stop your app launching at install
if (require('electron-squirrel-startup')) {
  app.quit();
}

// get the lock to chen single instance
if (!app.requestSingleInstanceLock()) {
  app.quit();
}

// Add app to system startup
app.setLoginItemSettings({
  openAtLogin: app.isPackaged,
  openAsHidden: true,
  path: app.getPath("exe")
});


// Fix Flickering the window
app.commandLine.appendSwitch(
  'wm-window-animations-disabled'
);

// create necessary directories for the app
if (!fs.existsSync(C.APPLICATION_HOME)) {
  fs.mkdirSync(C.APPLICATION_HOME, { recursive: true })
}

// configure the electron-settings
configure({
  dir: C.APPLICATION_HOME,
  prettify: true,
  fileName: "prefs.json"
});


/******************************************
 *          App initialize section        *
 *****************************************/

// on app ready event
app.on('ready', async () => {
  // file store to store the note
  const fileStore = new FileStore(path.join(C.APPLICATION_HOME, ".quicknote"));

  // Restart App
  const restartApp = () => {
    app.relaunch();
    app.exit();
  }

  // ipc event for recv
  ipcMain.handle(E.RECV_IN_MAIN_CHAN, async (e, arg) => {
    return await fileStore.setNote(arg);
  });

  // ipc event for send
  ipcMain.handle(E.SEND_IN_MAIN_CHAN, async () => {
    return await fileStore.getNote();
  });

  // ipc event for error
  ipcMain.on(E.ONER_IN_MAIN_CHAN, async (e, arg) => {
    dialog.showErrorBox(C.APPLICATION_NAME, arg);
    restartApp();
  });

  // create the tray window
  const noteWindow = new TrayWindow({
    tooltip: C.APPLICATION_NAME,
    icon: C.APPLICATION_ICON,
    trayIcon: C.APPLICATION_ICON,
    frame: false,
    show: false,
    skipTaskbar: true,
    resizable: true,
    transparent: true,
    hasShadow: true,
    alwaysOnTop: true,
    webPreferences: {
      preload: NOTE_WINDOW_PRELOAD_WEBPACK_ENTRY,
      devTools: !app.isPackaged
    }
  });

  // set the size of the QuickNote window
  noteWindow.setSize(...await settings.getWindowSize(C.APPLICATION_SIZE));

  // on resized event save the size
  noteWindow.on('resized', () => {
    settings.setWindowSize(noteWindow.getSize() as [number, number]);
  });

  // global hot key ctrl + alt + q
  globalShortcut.register("CommandOrControl+Alt+Q", () => {
    noteWindow.showNearCursor();
  });

  // set the context menu for the tray
  noteWindow.tray.setContextMenu(Menu.buildFromTemplate([
    { label: 'About Us', click: () => open(C.APPLICATION_URL) },
    { label: 'Report', click: () => open(C.ISSUE_RAISE_URL) },
    { label: 'Donate', click: () => open(C.APP_DONATE_URL) },
    { type: 'separator' },
    { label: 'Quit', click: () => app.quit() }
  ]));

  // Create Non visible Menu
  const menu = new Menu();

  // Add ESC to close the app
  menu.append(new MenuItem({
    label: 'Hide QuickNote',
    visible: false,
    accelerator: 'Esc',
    click: () => {
      noteWindow.hide();
    }
  }));

  // Add for dev tools
  menu.append(new MenuItem({
    enabled: !app.isPackaged,
    label: "Open Dev Tools",
    visible: false,
    accelerator: "Control+D",
    click: () => {
      noteWindow.webContents.openDevTools();
    }
  }));

  // Add for Quit
  menu.append(new MenuItem({
    label: "Quit QuickNote",
    visible: false,
    accelerator: "Control+Q",
    click: () => {
      app.quit();
    }
  }));

  // set the menu
  Menu.setApplicationMenu(menu);

  // on app exit
  app.on('quit', async () => noteWindow.tray.destroy());

  // load the note window
  noteWindow.loadURL(NOTE_WINDOW_WEBPACK_ENTRY);
});
