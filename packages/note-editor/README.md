# @quicknote/editor

The QuickNote rich text editor (Lexical), shared by the desktop app and the website. It ships as source, so the consuming bundler (Vite) compiles it.

```tsx
import { NoteEditor } from "@quicknote/editor";

<NoteEditor initialContent={null} placeHolder="Put Something Here..." onError={console.error} />
```

Consumers need CSS modules (`*.module.css`) support, which Vite has built in.
