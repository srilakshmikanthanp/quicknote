// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

const { screen } = require('electron');
const os = require('os');
const lib = require("./build/Release/tbarpos.node");

/**
 * Calculate the position of taskbar of the current display
 *
 * @return {"left" | "right" | "top" | "bottom" | "unknown"}
 */
function getTaskbarPosition() {
  const quicknoteDisplay = screen.getPrimaryDisplay();
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
    return "left";
  }

  // This condition must checked before the bottom condition
  if (workArea.y > 0) {
    // |||||||||||||||||||
    // |                 |
    // |   work area     |
    // |                 |
    // |-----------------|
    // <---- bounds ----->
    return "top";
  }

  if (workArea.width < bounds.width) {
    // |---------------|||
    // |               |||
    // |   work area   |||
    // |               |||
    // |---------------|||
    // <---- bounds ----->
    return "right";
  }

  if (workArea.height < bounds.height) {
    // |-----------------|
    // |                 |
    // |   work area     |
    // |                 |
    // |||||||||||||||||||
    // <---- bounds ----->
    return "bottom";
  }

  // Unknow taskbar location
  return "unknown";
}

/**
 * A Function to get task bar Position
 * @return {"left" | "right" | "top" | "bottom" | "unknown"}
 */
export function taskbarPosition() {
  switch(os.platform) {
    case 'win32':
      return lib.taskbarPosition();
    default:
      return getTaskbarPosition();
  }
}
