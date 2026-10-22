import { LuDownload, LuGitBranch } from 'react-icons/lu';
import ExternalLink from './ExternalLink';
import NoteWindow from './NoteWindow';

const SNAPSHOT_RELEASE_URL = 'https://github.com/srilakshmikanthanp/quicknote/releases/tag/snapshot';
const LATEST_RELEASE_URL = 'https://github.com/srilakshmikanthanp/quicknote/releases/latest';

const button = 'flex flex-1 items-center justify-center gap-2 rounded-full px-6 py-3 font-medium transition hover:-translate-y-0.5 sm:flex-none';

export default function Introduction() {
  return (
    <section className="grid items-center gap-14 md:grid-cols-2">
      <div>
        <div className="flex items-center gap-2 text-sm text-muted">
          <kbd className="keycap">Ctrl</kbd>+<kbd className="keycap">Alt</kbd>+<kbd className="keycap">Q</kbd>
        </div>

        <h1 className="my-6 font-serif text-5xl leading-[1.05] font-bold tracking-tight md:text-7xl">
          Jot it down
          <br />
          <em className="font-medium text-accent">before it's gone.</em>
        </h1>

        <p className="max-w-md text-lg text-muted">
          A tiny note that lives in your system tray. Open it with a shortcut, write, and walk away. It saves itself.
        </p>

        <div className="mt-8 flex gap-3">
          <ExternalLink href={LATEST_RELEASE_URL} className={`${button} bg-fg text-bg`}>
            <LuDownload /> Download
          </ExternalLink>
          <ExternalLink href={SNAPSHOT_RELEASE_URL} className={`${button} border border-line bg-paper`}>
            <LuGitBranch /> Snapshot
          </ExternalLink>
        </div>
        <p className="mt-4 text-sm text-muted">Free &amp; open source · Windows &amp; Linux</p>
      </div>

      <NoteWindow />
    </section>
  );
}
