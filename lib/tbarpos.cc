// Copyright (c) 2023 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

#if defined(_WIN32) || defined(_WIN64)
// clang-format off
#include <windows.h>
#include <shellapi.h>
// clang-format on
#endif

#include <node.h>
#include <v8.h>

#include <string>

namespace tbarpos {

/**
 * @brief Detect the task bar position of Windows
 * @return "bottom" |"top" | left" | "right" | "unknown" | "error"
 */
std::string GetTaskbarPosition() {
#if defined(_WIN32) || defined(_WIN64)
  // APPBARDATA structure
  APPBARDATA appBarData = {0};

  // Initialize the APPBARDATA structure
  appBarData.cbSize = sizeof(appBarData);

  // Get taskbar position
  HRESULT result = SHAppBarMessage(ABM_GETTASKBARPOS, &appBarData);

  // if not success return error
  if (!SUCCEEDED(result)) {
    return "unknown";
  }

  // Determine the taskbar orientation based on its edge
  switch (appBarData.uEdge) {
    case ABE_BOTTOM:
      return "bottom";
    case ABE_LEFT:
      return "left";
    case ABE_TOP:
      return "top";
    case ABE_RIGHT:
      return "right";
  }
#endif

  // return unknown
  return "unknown";
}

// using v8 elements
using v8::FunctionCallbackInfo;
using v8::Isolate;
using v8::Local;
using v8::Object;
using v8::String;
using v8::Value;

/**
 * @brief Node Method
 */
void TaskbarPosition(const v8::FunctionCallbackInfo<v8::Value> &args) {
  // Get the taskbar position
  std::string position = GetTaskbarPosition();
  v8::Isolate *isolate = args.GetIsolate();

  // Return the taskbar position
  args.GetReturnValue().Set(
      String::NewFromUtf8(isolate, position.c_str()).ToLocalChecked());
}

/**
 * @brief Initialize the module
 */
void Initialize(Local<Object> exports) {
  NODE_SET_METHOD(exports, "taskbarPosition", TaskbarPosition);
}

// Do Initialize
NODE_MODULE(NODE_GYP_MODULE_NAME, Initialize)

}  // namespace lib
