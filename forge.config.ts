import type { ForgeConfig } from '@electron-forge/shared-types';
import { MakerSquirrel } from '@electron-forge/maker-squirrel';
import { MakerDeb } from '@electron-forge/maker-deb';
import { WebpackPlugin } from '@electron-forge/plugin-webpack';

import { mainConfig } from './webpack.main.config';
import { rendererConfig } from './webpack.renderer.config';

const config: ForgeConfig = {
  packagerConfig: {
    icon: './src/assets/images/quicknote',
  },
  rebuildConfig: {},
  makers: [
    new MakerSquirrel({
      loadingGif: './src/assets/images/loading.gif',
      iconUrl: './src/assets/images/quicknote.ico',
      setupIcon: './src/assets/images/quicknote.ico',
    }),
    new MakerDeb({
      options: {
        homepage: 'https://github.com/srilakshmikanthanp/quicknote',
        icon: './src/assets/images/quicknote.png',
      },
    })
  ],
  plugins: [
    new WebpackPlugin({
      mainConfig,
      renderer: {
        config: rendererConfig,
        entryPoints: [
          {
            html: './src/html/index.html',
            js: './src/render/main.tsx',
            name: 'note_window',
            preload: {
              js: './src/electron/preload/NotePreload.ts',
            },
          },
        ],
      },
    }),
  ],
};

export default config;
