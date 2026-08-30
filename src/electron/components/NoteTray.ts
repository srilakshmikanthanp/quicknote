// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { Menu, Tray, app } from "electron";
import open from "open";
import * as C from "../constants/constants";

export default class NoteTray {
  private _tray: Tray;

  private _buildMenu(): Menu {
    return Menu.buildFromTemplate([
      { label: "About Us", click: () => open(C.APPLICATION_URL) },
      { label: "Report",   click: () => open(C.ISSUE_RAISE_URL) },
      { label: "Donate",   click: () => open(C.APP_DONATE_URL) },
      { type: "separator" },
      { label: "Quit",     click: () => app.quit() },
    ]);
  }

  public constructor(onClick: () => void) {
    this._tray = new Tray(C.APPLICATION_ICON);
    this._tray.setToolTip(C.APPLICATION_NAME);
    this._tray.on("click", onClick);
    this._tray.setContextMenu(this._buildMenu());
  }

  public destroy(): void {
    this._tray.destroy();
  }
}
