import { useState } from 'react';
import { LuBold, LuItalic, LuUnderline } from 'react-icons/lu';

const INITIAL = `Buy oat milk
Call the plumber at 5
Idea: tray note for everything ✨`;

export default function NoteWindow() {
  const [text, setText] = useState(INITIAL);

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
        <textarea
          aria-label="Try it: type a note"
          value={text}
          onChange={e => setText(e.target.value)}
          spellCheck={false}
          rows={7}
          className="block w-full resize-none bg-transparent p-4 leading-8 outline-none"
          style={{
            backgroundImage: 'linear-gradient(transparent 31px, var(--color-line) 32px)',
            backgroundSize: '100% 32px',
            backgroundPositionY: '10px'
          }}
        />
        <div className="flex items-center gap-1.5 border-t border-line px-4 py-2 text-xs text-muted">
          <span className="size-1.5 rounded-full bg-emerald-500" /> Saved
        </div>
      </div>
    </div>
  );
}
