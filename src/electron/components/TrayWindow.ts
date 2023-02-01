// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { BrowserWindow, BrowserWindowConstructorOptions, Tray, screen } from 'electron';

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
  trayIcon: string;
  index: string;
  tooltip: string;
}

// TrayWindow to create window near system tray
export default class TrayWindow extends BrowserWindow {
  // Tray Instance Associated with the TrayWindow & used to calculate the position of the window
  private _tray: Tray;

  /**
   * Position the window on bottom
   */
  private _positionOnBottom(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(((icoRect.width / 2) + icoRect.x) - (winSize[0] / 2));
    const newPosY = Math.floor(icoRect.y - winSize[1]);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on top
   */
  private _positionOnTop(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(((icoRect.width / 2) + icoRect.x) - (winSize[0] / 2));
    const newPosY = Math.floor(icoRect.y + icoRect.height);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on left
   */
  private _positionOnLeft(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(icoRect.x + icoRect.width);
    const newPosY = Math.floor(((icoRect.height / 2) + icoRect.y) - (winSize[1] / 2));
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on right
   */
  private _positionOnRight(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(icoRect.x - winSize[0]);
    const newPosY = Math.floor(((icoRect.height / 2) + icoRect.y) - (winSize[1] / 2));
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Calculate the position of taskbar of the current display
   * 
   * @return top | bottom | left | right
   */
  private _getTaskbarPosition(): 'top' | 'bottom' | 'left' | 'right' {
    const quicknoteDisplay = screen.getDisplayMatching(this.getBounds());
    const workArea = quicknoteDisplay.workArea;
    const bounds = quicknoteDisplay.bounds;

    // This condition must checked before the right condition
    if (workArea.x > 0) {
      // |||---------------|
      // |||               |
      // |||   work area   |
      // |||               |
      // |||---------------|
      // <---- bounds ----->
      return 'left';
    }

    // This condition must checked before the bottom condition
    if (workArea.y > 0) {
      // |||||||||||||||||||
      // |                 |  
      // |   work area     |
      // |                 |
      // |-----------------|
      // <---- bounds ----->
      return 'top';
    }

    if (workArea.width < bounds.width) {
      // |---------------|||
      // |               |||
      // |   work area   |||
      // |               |||
      // |---------------|||
      // <---- bounds -----> 
      return 'right';
    }

    if (workArea.height < bounds.height) {
      // |-----------------|
      // |                 |
      // |   work area     |
      // |                 |
      // |||||||||||||||||||
      // <---- bounds ----->
      return 'bottom';
    }
  }

  /**
   * Calibrate the position
   */
  private _calibratePosition(): void {
    const bounds = screen.getDisplayMatching(this.getBounds()).workArea;
    const winSiz = this.getSize();
    const winPos = this.getPosition();

    if ( winPos[0] + winSiz[0] > bounds.width) {
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
   * On window Show event handler
   */
  private _onShowed(): void {
    // Set the initial position
    switch (this._getTaskbarPosition()) {
      case 'bottom' : this._positionOnBottom(); break;
      case 'left'   : this._positionOnLeft();   break;
      case 'top'    : this._positionOnTop();    break;
      case 'right'  : this._positionOnRight();  break;
    }

    // calibrate the position
    this._calibratePosition();
  }

  /**
   * Constructor for the NoteWindow class
   * @param options Options for the TrayWindow
   */
  public constructor(options: TrayWindowOptions) {
    // Initialize The Browser Window via super
    super(options);

    // on window show event handler
    this.on('show', () => this._onShowed());

    // on focus lost event handler
    this.on('blur', () => this.hide());

    // set the initial index
    this.loadURL(options.index)

    // Initialize the Tray Window
    this._tray = new Tray(options.trayIcon)

    // set the event handlers
    this._tray.on('click', () => this.show())

    // set the initial tooltip
    this._tray.setToolTip(options.tooltip)
  }

  /**
   * Tray instance Getter
   * 
   * @return Tray
   */
  public get tray(): Tray {
    return this._tray;
  }
}
