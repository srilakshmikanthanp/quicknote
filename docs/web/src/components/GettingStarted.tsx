interface Step {
  title: string;
  text: string;
}

const steps: Step[] = [
  { title: 'Install', text: 'Grab the .msi for Windows or the .deb for Linux from Releases.' },
  { title: 'Open', text: 'Click the tray icon or press Ctrl+Alt+Q.' },
  { title: 'Write', text: 'Type. It is saved automatically as you go.' },
  { title: 'Return', text: 'Open it later and pick up exactly where you left off.' }
];

export default function GettingStarted() {
  return (
    <section>
      <h2 className="mb-8 font-serif text-3xl font-bold md:text-4xl">Four steps, then never again.</h2>
      <ol className="grid gap-x-8 gap-y-6 md:grid-cols-2">
        {steps.map((s, i) => (
          <li key={s.title} className="flex gap-4 border-b border-dashed border-line pb-6">
            <span className="font-serif text-4xl leading-none text-accent italic">{i + 1}</span>
            <div>
              <h3 className="font-semibold">{s.title}</h3>
              <p className="text-muted">{s.text}</p>
            </div>
          </li>
        ))}
      </ol>
    </section>
  );
}
