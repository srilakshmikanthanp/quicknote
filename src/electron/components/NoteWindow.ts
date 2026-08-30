// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import {
  BrowserWindow,
  BrowserWindowConstructorOptions,
  Point,
  screen,
} from "electron";

export default class NoteWindow extends BrowserWindow {
  public constructor(options: BrowserWindowConstructorOptions) {
    super(options);
    this.on("blur", () => this.hide());
  }

  private fitToScreen(): void {
    const bounds = screen.getDisplayMatching(this.getBounds()).workArea;
    const [w, h] = this.getSize();
    let [x, y] = this.getPosition();
    x = Math.max(bounds.x, Math.min(x, bounds.x + bounds.width - w));
    y = Math.max(bounds.y, Math.min(y, bounds.y + bounds.height - h));
    this.setPosition(x, y);
  }

  public showNearPoint(p: Point): void {
    const [w] = this.getSize();
    const x = Math.floor(p.x - w / 2);
    const y = Math.floor(p.y);
    this.show();
    this.setPosition(x, y);
    this.fitToScreen();
    this.focus();
  }

  public showCentered(): void {
    const { workArea } = screen.getPrimaryDisplay();
    const [w, h] = this.getSize();
    const x = Math.floor(workArea.x + (workArea.width - w) / 2);
    const y = Math.floor(workArea.y + (workArea.height - h) / 2);
    this.show();
    this.setPosition(x, y);
    this.focus();
  }

  // Pinned to Electron v28.3.3: v29+ broke screen.getCursorScreenPoint() on
  // Linux X11 (electron/electron#42519). Fix lands in Chromium M154 → Electron
  // v46 (~Jan 2027), at which point this comment and the version pin can be removed.
  public showNote(): void {
    this.showNearPoint(screen.getCursorScreenPoint());
  }
}
