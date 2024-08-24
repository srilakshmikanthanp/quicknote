// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { taskbarPosition } from "../../../lib";

import {
  BrowserWindow,
  BrowserWindowConstructorOptions,
  KeyboardEvent,
  Point,
  Rectangle,
  Tray,
  screen,
} from "electron";

/**
 *
 *   ____            _   _
 *  / ___|__ _ _   _| |_(_) ___  _ __
 * | |   / _` | | | | __| |/ _ \| '_ \
 * | |__| (_| | |_| | |_| | (_) | | | |
 *  \____\__,_|\__,_|\__|_|\___/|_| |_|
 *
 * This file is part of QuickNote. which is tested very carefully
 * if you are going to modify this file, please make sure that you
 * are not breaking anything. Test it on right, top, left, bottom
 * position of taskbar on screen before committing. Thank you :)
 */

// Tray Window Options passed on TrayWindow Creation
export interface TrayWindowOptions extends BrowserWindowConstructorOptions {
  tray: Tray; tooltip: string;
}

// TrayWindow to create window near system tray
export default class TrayWindow extends BrowserWindow {
  /**
   * Position the window on bottom of Bounds
   */
  private _positionOnBottom(b: Rectangle): void {
    const winSize = this.getSize();
    const newPosX = Math.floor(b.width / 2 + b.x - winSize[0] / 2);
    const newPosY = Math.floor(b.y - winSize[1]);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on top of Bounds
   */
  private _positionOnTop(b: Rectangle): void {
    const winSize = this.getSize();
    const newPosX = Math.floor(b.width / 2 + b.x - winSize[0] / 2);
    const newPosY = Math.floor(b.y + b.height);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on left of Bounds
   */
  private _positionOnLeft(b: Rectangle): void {
    const winSize = this.getSize();
    const newPosX = Math.floor(b.x + b.width);
    const newPosY = Math.floor(b.height / 2 + b.y - winSize[1] / 2);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on right of Bounds
   */
  private _positionOnRight(b: Rectangle): void {
    const winSize = this.getSize();
    const newPosX = Math.floor(b.x - winSize[0]);
    const newPosY = Math.floor(b.height / 2 + b.y - winSize[1] / 2);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * On window Show event handler
   */
  private _show(bounds: Rectangle): void {
    // Get the position of the taskbar
    const position = taskbarPosition();

    // Show the window
    this.show();

    // Set the initial position
    switch (position) {
      case "bottom": this._positionOnBottom(bounds); break;
      case "left": this._positionOnLeft(bounds); break;
      case "top": this._positionOnTop(bounds); break;
      case "right": this._positionOnRight(bounds); break;
      default: this.showNearCursor(); break;
    }

    // calibrate the position
    this.fitToScreen();
  }

  /**
   * Constructor for the NoteWindow class
   * @param options Options for the TrayWindow
   */
  public constructor(options: TrayWindowOptions) {
    // Initialize The Browser Window via super
    super(options);

    // Initialize the Tray Window
    this._tray = options.tray;

    // on focus lost event handler
    this.on("blur", () => this.hide());

    // set the event handlers
    this._tray.on("click", (e, b, p) => {
      this._show(b);
    });

    // set the initial tooltip
    this._tray.setToolTip(options.tooltip);
  }

  // Tray Instance Associated with the TrayWindow
  private _tray: Tray;

  /**
   * Tray instance Getter
   *
   * @return Tray
   */
  public get tray(): Tray {
    return this._tray;
  }

  /**
   * Calibrate the position
   */
  public fitToScreen(): void {
    const bounds = screen.getDisplayMatching(this.getBounds()).workArea;
    const winSiz = this.getSize();
    const winPos = this.getPosition();

    if (winPos[0] + winSiz[0] > bounds.width) {
      this.setPosition(bounds.width - winSiz[0], winPos[1]);
    } else if (winPos[0] < bounds.x) {
      this.setPosition(bounds.x, winPos[1]);
    }

    if (winPos[1] + winSiz[1] > bounds.height) {
      this.setPosition(winPos[0], bounds.height - winSiz[1]);
    } else if (winPos[1] < bounds.y) {
      this.setPosition(winPos[0], bounds.y);
    }
  }

  /**
   * Show Near Point
   */
  public showNearPoint(p: Point): void {
    this.show();
    this.setPosition(p.x, p.y);
    this.fitToScreen();
  }

  /**
   * Show near cursor
   */
  public showNearCursor(): void {
    this.showNearPoint(screen.getCursorScreenPoint());
  }
}
