// Copyright (c) 2023 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

import { BrowserWindow, BrowserWindowConstructorOptions, Tray, screen } from 'electron';

// Tray Window Options passed on TrayWindow Creation
interface TrayWindowOptions extends BrowserWindowConstructorOptions {
  trayIcon: string;
  index: string;
  tooltip: string;
}

// TrayWindow to create window near system tray
export default class TrayWindow extends BrowserWindow {
  // Tray Instance Associated with the TrayWindow
  private _tray: Tray;

  /**
   * Calculate the position of taskbar of the current display
   * 
   * @return top | bottom | left | right
   */
  private _getTaskbarPosition(): 'top' | 'bottom' | 'left' | 'right' {
    const quicknoteDisplay = screen.getDisplayMatching(this.getBounds());
    const workArea = quicknoteDisplay.workArea;
    const bounds = quicknoteDisplay.bounds;

    if (workArea.width < bounds.width) {
      // |---------------|||
      // |               |||
      // |   work area   |||
      // |               |||
      // |---------------|||
      // <---- bounds -----> 
      return 'right';
    }

    if (workArea.x > 0) {
      // |||---------------|
      // |||               |
      // |||   work area   |
      // |||               |
      // |||---------------|
      // <---- bounds ----->
      return 'left';
    }

    if (workArea.y > 0) {
      // |||||||||||||||||||
      // |                 |  
      // |   work area     |
      // |                 |
      // |-----------------|
      // <---- bounds ----->
      return 'top';
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
    const newPosY = Math.floor(icoRect.y + winSize[1]);
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on left
   */
  private _positionOnLeft(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(icoRect.x + winSize[1]);
    const newPosY = Math.floor(((icoRect.height / 2) + icoRect.y) - (winSize[0] / 2));
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Position the window on right
   */
  private _positionOnRight(): void {
    const icoRect = this._tray.getBounds();
    const winSize = this.getSize();
    const newPosX = Math.floor(icoRect.x - winSize[1]);
    const newPosY = Math.floor(((icoRect.height / 2) + icoRect.y) - (winSize[0] / 2));
    this.setPosition(newPosX, newPosY);
  }

  /**
   * Calibrate the position
   */
  private _calibratePosition() : void {
    const bounds = screen.getDisplayMatching(this.getBounds()).bounds;
    const winSiz = this.getSize();
    const winPos = this.getPosition();

    if (winSiz[0] + winPos[0] > bounds.width) {
      // handle x overflow
    } else if (winPos[0] < bounds.x) {
      // handle x underflow
    }

    if (winSiz[1] + winPos[1] > bounds.height) {
      // handle y overflow
    } else if(winPos[1] < bounds.y) {
      // handle y underflow
    }
  }

  /**
   * On window Show event handler
   */
  private _onShow(): void {
    // Set the initial position
    switch (this._getTaskbarPosition()) {
      case 'bottom':
        this._positionOnBottom();
        break;
      case 'left':
        this._positionOnLeft();
        break;
      case 'top':
        this._positionOnTop();
        break;
      case 'right':
        this._positionOnRight();
        break;
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

    // Initialize the Tray Window
    this._tray = new Tray(options.trayIcon)

    // set the event handlers
    this._tray.on('click', () => this.show())

    // on show event handler
    this.on('show', () => this._onShow());

    // set the initial index
    this.loadURL(options.index)

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
