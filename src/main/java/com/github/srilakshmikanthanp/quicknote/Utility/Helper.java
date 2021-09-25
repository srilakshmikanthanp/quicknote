// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Utility;

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
     * set theme to scene
     */
    public static void setTheme(Scene scene) {
       switch(Prefs.getTheme()) {
            case Prefs.MODENA_THEME:
                scene.getStylesheets().clear();
                break;
            case Prefs.CASPIAN_THEME:
                scene.getStylesheets().add(
                    Helper.class.getResource("/styles/caspian.css").toExternalForm()
                );
                break;
       }
    }
}
