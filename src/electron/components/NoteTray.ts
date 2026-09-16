// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { Menu, Tray, app, type MenuItemConstructorOptions } from "electron";
import open from "open";
import * as C from "../constants/constants";
import type { ThemeSource } from "../settings";

export default class NoteTray {
  private _tray: Tray;
  private _getThemeSource: () => Promise<ThemeSource>;
  private _onThemeSourceChange: (themeSource: ThemeSource) => Promise<void>;

  private _onThemeSourceChanged: (themeSource: ThemeSource) => Promise<void> = async (themeSource) => {
    await this._onThemeSourceChange(themeSource);
    await this._refreshMenu();
  };

  private _buildThemeMenu(themeSource: ThemeSource): MenuItemConstructorOptions[] {
    return (["system", "light", "dark"] as ThemeSource[]).map((optionThemeSource) => ({
      label: optionThemeSource.charAt(0).toUpperCase() + optionThemeSource.slice(1),
      type: "checkbox",
      checked: themeSource === optionThemeSource,
      click: () => this._onThemeSourceChanged(optionThemeSource),
    }));
  }

  private _buildMenu(themeSource: ThemeSource): Menu {
    return Menu.buildFromTemplate([
      { label: "About Us", click: () => open(C.APPLICATION_URL) },
      { label: "Report",   click: () => open(C.ISSUE_RAISE_URL) },
      { label: "Donate",   click: () => open(C.APP_DONATE_URL) },
      { type: "separator" },
      { label: "Theme", submenu: this._buildThemeMenu(themeSource) },
      { type: "separator" },
      { label: "Quit",     click: () => app.quit() },
    ]);
  }

  private async _refreshMenu(): Promise<void> {
    const themeSource = await this._getThemeSource();
    this._tray.setContextMenu(this._buildMenu(themeSource));
  }

  public constructor(
    onClick: () => void,
    getThemeSource: () => Promise<ThemeSource>,
    onThemeSourceChange: (themeSource: ThemeSource) => Promise<void>
  ) {
    this._getThemeSource = getThemeSource;
    this._onThemeSourceChange = onThemeSourceChange;
    this._tray = new Tray(C.APPLICATION_ICON);
    this._tray.setToolTip(C.APPLICATION_NAME);
    this._tray.on("click", onClick);
    void this._refreshMenu();
  }

  public destroy(): void {
    this._tray.destroy();
  }
}
