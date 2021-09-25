module quicknote {
    // from java std
    requires transitive java.prefs;
    requires transitive java.desktop;

    // from javafx
    requires transitive javafx.controls;

    exports com.github.srilakshmikanthanp.quicknote;
    exports com.github.srilakshmikanthanp.quicknote.Application;
}
