import FeatureList from './components/FeatureList';
import Footer from './components/Footer';
import GettingStarted from './components/GettingStarted';
import Header from './components/Header';
import Introduction from './components/Introduction';
import Shortcuts from './components/Shortcuts';

export default function App() {
  return (
    <div className="mx-auto flex min-h-screen max-w-5xl flex-col px-5 md:px-8">
      <Header />

      <main className="flex flex-1 flex-col gap-24 py-10 md:py-16">
        <Introduction />
        <FeatureList />
        <Shortcuts />
        <GettingStarted />
      </main>

      <Footer />
    </div>
  );
}
