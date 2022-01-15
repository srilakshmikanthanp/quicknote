module quicknote {
    // from java std
    requires java.prefs;
    requires java.desktop;

    // from javafx
    requires javafx.controls;

    // from jmetro
    requires org.jfxtras.styles.jmetro;

    // required ascess
    opens com.github.srilakshmikanthanp.quicknote to javafx.graphics;
}
