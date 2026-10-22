import type { ReactNode } from 'react';
import { LuKeyboard, LuMonitorSmartphone, LuMoon, LuSave, LuTextCursorInput, LuZap } from 'react-icons/lu';

interface Feature {
  icon: ReactNode;
  title: string;
  text: string;
  tilt: string;
}

const features: Feature[] = [
  {
    icon: <LuZap />,
    title: 'Always in the tray',
    text: 'One click on the icon and your note is open.',
    tilt: '-rotate-1'
  },
  {
    icon: <LuKeyboard />,
    title: 'Global shortcut',
    text: 'Ctrl+Alt+Q works from any app.',
    tilt: 'rotate-1'
  },
  {
    icon: <LuSave />,
    title: 'Auto-saved',
    text: 'No save button. Just close it.',
    tilt: '-rotate-2'
  },
  {
    icon: <LuTextCursorInput />,
    title: 'Light rich text',
    text: 'Bold, italic and underline when you need them.',
    tilt: 'rotate-2'
  },
  {
    icon: <LuMoon />,
    title: 'Light & dark',
    text: 'Follows your system, or pick a theme.',
    tilt: '-rotate-1'
  },
  {
    icon: <LuMonitorSmartphone />,
    title: 'Windows & Linux',
    text: 'Native installers for both.',
    tilt: 'rotate-1'
  }
];

export default function FeatureList() {
  return (
    <section>
      <h2 className="mb-8 font-serif text-3xl font-bold md:text-4xl">Small on purpose.</h2>
      <ul className="grid gap-5 sm:grid-cols-2 md:grid-cols-3">
        {features.map((f, i) => (
          <li
            key={f.title}
            className={`${f.tilt} rounded-lg border border-line bg-paper p-5 shadow-md transition-transform hover:-translate-y-1 hover:rotate-0 ${i === 0 ? 'bg-sticky! text-sticky-fg' : ''}`}
          >
            <span className="mb-3 block text-xl text-accent">{f.icon}</span>
            <h3 className="font-semibold">{f.title}</h3>
            <p className="mt-1 text-sm opacity-75">{f.text}</p>
          </li>
        ))}
      </ul>
    </section>
  );
}
