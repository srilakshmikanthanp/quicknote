import { lazy, Suspense, useState } from 'react';
import { SEED_NOTE } from './noteSeed';

const NoteEditor = lazy(() => import('@quicknote/editor'));

export default function NoteWindow() {
  const [pinned, setPinned] = useState(false);

  return (
    <div className="relative transition-transform hover:rotate-0 sm:-rotate-2">
      <div className="absolute inset-0 translate-x-3 translate-y-3 rounded-xl bg-sticky opacity-70" />
      <div className="demo-window demo-note relative h-80 overflow-hidden rounded-[10px] border border-(--note-border) bg-(--editor-bg-color) shadow-2xl">
        <Suspense fallback={null}>
          <NoteEditor
            initialContent={SEED_NOTE}
            placeHolder="Put something here..."
            onError={error => console.error(error)}
            autoFocus={false}
            toolbar
            titleBar
            pinned={pinned}
            onTogglePin={() => setPinned(p => !p)}
            icon={<img src="/logo.png" width={18} height={18} alt="" />}
          />
        </Suspense>
      </div>
    </div>
  );
}
