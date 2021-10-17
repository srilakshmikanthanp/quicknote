// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Utility;

import java.util.prefs.*;

/**
 * USer Preference class
 */
public class Prefs {
    /**
     * Preference node name
     */
    public final static Preferences prefs = Preferences.userNodeForPackage(Prefs.class);

    /*********************************************************/
    /**                         Height                      **/
    /*********************************************************/

    public static final String HEIGHT_PREF_KEY = "HEIGHT";
    public static final double MIN_HEIGHT = 100;
    public static final double MAX_HEIGHT = 500;
    public static final double DEFAULT_HEIGHT = 350;

    /**
     * setter of the height of the Note.
     */
    public static void setHeight(double height) {
        Prefs.prefs.putDouble(HEIGHT_PREF_KEY, height);
    }
    
    /**
     * getter of the width of the Note.
     */
    public static double getHeight() {
        return Prefs.prefs.getDouble(HEIGHT_PREF_KEY, DEFAULT_HEIGHT);
    }

    /********************************************************/
    /**                         Width                      **/
    /********************************************************/

    public static final String WIDTH_PREF_KEY = "WIDTH";
    public static final double MIN_WIDTH = 100;
    public static final double MAX_WIDTH = 550;
    public static final double DEFAULT_WIDTH = 350;

    /**
     * setter of the width of the Note.
     */
    public static void setWidth(double width) {
        Prefs.prefs.putDouble(WIDTH_PREF_KEY, width);
    }
    
    /**
     * getter of the width of the Note.
     */
    public static double getWidth() {
        return Prefs.prefs.getDouble(WIDTH_PREF_KEY, DEFAULT_WIDTH);
    }

    /********************************************************/
    /**                         Themes                     **/
    /********************************************************/

    public static final String THEME_PREF_KEY = "THEME";
    public static final String LIGHT_THEME = "Light";
    public static final String DARK_THEME = "Dark";
    public static final String DEFAULT_THEME = DARK_THEME;


    /**
     * setter of the theme of the Note.
     */
    public static void setTheme(String theme) {
        Prefs.prefs.put(THEME_PREF_KEY, theme);
    }

    /**
     * getter of the theme of the Note.
     */
    public static String getTheme() {
        return Prefs.prefs.get(THEME_PREF_KEY, DEFAULT_THEME);
    }

    /********************************************************/
    /**                         Lock Focus                 **/
    /********************************************************/
    
    public static final String LOCK_FOCUS_PREF_KEY = "LOCK_FOCUS";
    public static final boolean DEFAULT_LOCK_FOCUS = false;

    /**
     * setter of the lock focus of the Note.
     */
    public static void setLockFocus(boolean lockFocus) {
        Prefs.prefs.putBoolean(LOCK_FOCUS_PREF_KEY, lockFocus);
    }

    /**
     * getter of the lock focus of the Note.
     */
    public static boolean getLockFocus() {
        return Prefs.prefs.getBoolean(LOCK_FOCUS_PREF_KEY, DEFAULT_LOCK_FOCUS);
    }

    /********************************************************/
    /**                         Text                       **/
    /********************************************************/

    public static final String TEXT_PREF_KEY = "TEXT";
    public static final String DEFAULT_TEXT = "";

    /**
     * setter of the text of the Note.
     */
    public static void setText(String text) {
        Prefs.prefs.put(TEXT_PREF_KEY, text);
    }

    /**
     * getter of the text of the Note.
     */
    public static String getText() {
        return Prefs.prefs.get(TEXT_PREF_KEY, DEFAULT_TEXT);
    }
}
