interface Shortcut {
  label: string;
  keys: string[];
}

const shortcuts: Shortcut[] = [
  { label: 'Bold', keys: ['Ctrl', 'B'] },
  { label: 'Italic', keys: ['Ctrl', 'I'] },
  { label: 'Underline', keys: ['Ctrl', 'U'] },
  { label: 'Bullet list', keys: ['-', 'Space'] },
  { label: 'Numbered list', keys: ['1.', 'Space'] },
  { label: 'Checklist', keys: ['[ ]', 'Space'] },
  { label: 'Indent item', keys: ['Tab'] },
  { label: 'Outdent item', keys: ['Shift', 'Tab'] },
  { label: 'Open a link', keys: ['Ctrl', 'Click'] },
  { label: 'Hide the note', keys: ['Esc'] }
];

export default function Shortcuts() {
  return (
    <section>
      <h2 className="mb-2 font-serif text-3xl font-bold md:text-4xl">Write fast.</h2>
      <p className="mb-8 max-w-xl text-muted">
        Try them in the note at the top of the page. Links are detected as you type. In the app, pin the note from the
        pin icon to keep it open and drag it around.
      </p>
      <ul className="grid gap-x-8 sm:grid-cols-2">
        {shortcuts.map(s => (
          <li key={s.label} className="flex items-center justify-between border-b border-dashed border-line py-3">
            <span>{s.label}</span>
            <span className="flex items-center gap-1.5 text-sm text-muted">
              {s.keys.map((k, i) => (
                <span key={k} className="flex items-center gap-1.5">
                  {i > 0 && '+'}
                  <kbd className="keycap">{k}</kbd>
                </span>
              ))}
            </span>
          </li>
        ))}
      </ul>
    </section>
  );
}
