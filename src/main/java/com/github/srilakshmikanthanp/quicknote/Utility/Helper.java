// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Utility;

import javafx.collections.*;
import javafx.scene.Scene;

/**
 * Helper Class
 */
public class Helper {
    /**
     * Minumum width of window
     */
    public static final double MIN_WIN_WIDTH = 800;

    /**
     * Minimum height of window
     */
    public static final double MIN_WIN_HEIGHT = 650;

    /**
     * get the Available Themes
     */
    public static ObservableList<String> getAvailableThemes() {
        return FXCollections.observableArrayList(
            Prefs.DEFAULT_THEME
        );
    }

    /**
     * set theme to scene
     */
    public static void setTheme(Scene scene) {
       // ToDo: set theme
    }
}
