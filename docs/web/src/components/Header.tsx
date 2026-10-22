import { FaGithub } from 'react-icons/fa';
import ExternalLink from './ExternalLink';

const REPO_URL = 'https://github.com/srilakshmikanthanp/quicknote';

export default function Header() {
  return (
    <header className="flex items-center justify-between py-6">
      <a href="/" className="flex items-center gap-2 font-serif text-xl font-bold">
        <img src="/logo.png" width={28} height={28} alt="" />
        QuickNote
      </a>
      <ExternalLink
        href={REPO_URL}
        aria-label="GitHub"
        className="text-2xl text-muted transition-colors hover:text-accent"
      >
        <FaGithub />
      </ExternalLink>
    </header>
  );
}
