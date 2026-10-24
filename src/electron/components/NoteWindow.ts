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
  private pinned = false;

  public constructor(options: BrowserWindowConstructorOptions) {
    super(options);
    this.on("blur", () => !this.pinned && this.hide());
  }

  private fitToScreen(): void {
    const bounds = screen.getDisplayMatching(this.getBounds()).workArea;
    const [w, h] = this.getSize();
    let [x, y] = this.getPosition();
    x = Math.max(bounds.x, Math.min(x, bounds.x + bounds.width - w));
    y = Math.max(bounds.y, Math.min(y, bounds.y + bounds.height - h));
    this.setPosition(x, y);
  }

  public isPinned(): boolean {
    return this.pinned;
  }

  public setPinned(pinned: boolean): void {
    this.pinned = pinned;
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

  public showNote(): void {
    if (this.isVisible() && this.pinned) {
      this.focus();
    } else {
      this.showNearPoint(screen.getCursorScreenPoint());
    }
  }
}
