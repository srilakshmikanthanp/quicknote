import { FaGithub } from 'react-icons/fa';
import { LuHeart } from 'react-icons/lu';
import ExternalLink from './ExternalLink';

const SOURCE_URL = 'https://github.com/srilakshmikanthanp/quicknote';
const DONATE_URL = 'https://srilakshmikanthanp.github.io/donate/';

const link = 'flex items-center gap-1.5 transition-colors hover:text-accent';

export default function Footer() {
  return (
    <footer className="flex flex-col-reverse items-center gap-3 border-t border-line py-6 text-sm text-muted sm:flex-row sm:justify-between">
      <span>© {new Date().getFullYear()} QuickNote · MIT License</span>
      <nav className="flex gap-5">
        <ExternalLink href={SOURCE_URL} className={link}>
          <FaGithub /> Contribute
        </ExternalLink>
        <ExternalLink href={DONATE_URL} className={link}>
          <LuHeart /> Sponsor
        </ExternalLink>
      </nav>
    </footer>
  );
}
