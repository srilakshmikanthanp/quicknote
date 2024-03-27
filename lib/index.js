// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

// eslint-disable-next-line @typescript-eslint/no-var-requires
const lib = require("./build/Release/lib.node");

/**
 * A Function to get task bar Position
 * @return {"left" | "right" | "top" | "bottom" | "unknown"}
 */
export function taskbarPosition() {
  return lib.taskbarPosition();
}
