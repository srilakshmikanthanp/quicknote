module quicknote {
    // from java std
    requires java.prefs;
    requires java.desktop;

    // from javafx
    requires javafx.controls;

    // required ascess
    opens com.github.srilakshmikanthanp.quicknote to javafx.graphics;
}
