import type { ForgeConfig } from "@electron-forge/shared-types";
import { WebpackPlugin } from "@electron-forge/plugin-webpack";

import { mainConfig } from "./webpack.main.config";
import { rendererConfig } from "./webpack.renderer.config";

const config: ForgeConfig = {
  packagerConfig: {
    icon: "./src/assets/images/quicknote",
    extraResource: [
      "./src/assets/images/quicknote.png",
      "./src/assets/images/quicknote.gif",
      "./src/assets/images/quicknote.ico",
    ],
  },
  rebuildConfig: {},
  makers: [],
  plugins: [
    new WebpackPlugin({
      mainConfig,
      renderer: {
        config: rendererConfig,
        entryPoints: [
          {
            html: "./src/html/index.html",
            js: "./src/render/main.tsx",
            name: "note_window",
            preload: {
              js: "./src/electron/preload/NotePreload.ts",
            },
          },
        ],
      },
    }),
  ],
};

export default config;
