module quicknote {
    requires transitive java.prefs;
    requires transitive javafx.controls;
    requires transitive java.desktop;

    exports com.github.srilakshmikanthanp.quicknote;
    exports com.github.srilakshmikanthanp.quicknote.Application;
}
