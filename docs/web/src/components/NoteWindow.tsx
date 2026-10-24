import type { CSSProperties } from 'react';
import { lazy, Suspense } from 'react';
import { LuBold, LuItalic, LuUnderline } from 'react-icons/lu';
import { SEED_NOTE } from './noteSeed';

const NoteEditor = lazy(() => import('@quicknote/editor'));

const themeVars = {
  '--editor-bg-color': 'var(--color-paper)',
  '--editor-fg-color': 'var(--color-fg)'
} as CSSProperties;

export default function NoteWindow() {
  return (
    <div className="relative transition-transform hover:rotate-0 sm:-rotate-2">
      <div className="absolute inset-0 translate-x-3 translate-y-3 rounded-xl bg-sticky opacity-70" />
      <div className="relative overflow-hidden rounded-xl border border-line bg-paper shadow-2xl">
        <div className="flex items-center justify-between border-b border-line px-4 py-2.5">
          <span className="flex items-center gap-2 text-xs font-medium text-muted">
            <img src="/logo.png" width={16} height={16} alt="" /> QuickNote
          </span>
          <span className="flex gap-2 text-muted" aria-hidden>
            <LuBold /> <LuItalic /> <LuUnderline />
          </span>
        </div>
        <div className="demo-note h-60" style={themeVars}>
          <Suspense fallback={null}>
            <NoteEditor
              initialContent={SEED_NOTE}
              placeHolder="Put something here..."
              onError={error => console.error(error)}
              autoFocus={false}
            />
          </Suspense>
        </div>
        <div className="flex items-center gap-1.5 border-t border-line px-4 py-2 text-xs text-muted">
          <span className="size-1.5 rounded-full bg-emerald-500" /> Saved
        </div>
      </div>
    </div>
  );
}
