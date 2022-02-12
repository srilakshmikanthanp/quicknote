package com.github.srilakshmikanthanp.quicknote.utility

import javafx.scene.Scene
import javafx.scene.control.Alert

/**
 * Utility functions for the Application
 */
object UtilityFuns {
    /**
     * Changes the Theme of the Window
     *
     * @param scene
     */
    public fun syncTheme(scene: Scene) {
        // clear the previoue theme
        scene.stylesheets.clear();

        // Assume user selects Light
        var cssSheet = "/styles/Light.css"

        // get the theme from prefs
        if(Preference.isDark()) {
            cssSheet = "/styles/Dark.css"
        }

        // get the styleSheet
        val styleSheet = UtilityFuns.javaClass.getResource(cssSheet);

        // set the Theme to scene
        if(styleSheet != null) {
            scene.stylesheets.add(styleSheet.toExternalForm())
        } else {
            val alert = Alert(Alert.AlertType.ERROR);
            alert.contentText = "StyleSheet Not Found";
            alert.showAndWait();
        }
    }
}
