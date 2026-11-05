import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config
export default defineConfig({
  plugins: [react()],
  // the workspace package is source, load it directly so edits and renames apply without a stale cache
  optimizeDeps: { exclude: ['@quicknote/editor'] },
});
