// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.utility;

import java.util.prefs.Preferences;
import java.util.prefs.PreferenceChangeListener;

/**
 * User Preference class
 */
public class Preference {
    /**
     * Preference node name
     */
    private final static Preferences prefs = Preferences.userNodeForPackage(Preference.class);

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
        Preference.prefs.putDouble(HEIGHT_PREF_KEY, height);
    }
    
    /**
     * getter of the width of the Note.
     */
    public static double getHeight() {
        return Preference.prefs.getDouble(HEIGHT_PREF_KEY, DEFAULT_HEIGHT);
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
        Preference.prefs.putDouble(WIDTH_PREF_KEY, width);
    }
    
    /**
     * getter of the width of the Note.
     */
    public static double getWidth() {
        return Preference.prefs.getDouble(WIDTH_PREF_KEY, DEFAULT_WIDTH);
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
        Preference.prefs.put(THEME_PREF_KEY, theme);
    }

    /**
     * getter of the theme of the Note.
     */
    public static String getTheme() {
        return Preference.prefs.get(THEME_PREF_KEY, DEFAULT_THEME);
    }

    /********************************************************/
    /**                         Lock Focus                 **/
    /********************************************************/
    
    public static final String LOCK_PREF_KEY = "LOCK_FOCUS";
    public static final boolean DEFAULT_LOCK = false;

    /**
     * setter of the lock focus of the Note.
     */
    public static void setLock(boolean lockFocus) {
        Preference.prefs.putBoolean(LOCK_PREF_KEY, lockFocus);
    }

    /**
     * getter of the lock focus of the Note.
     */
    public static boolean getLock() {
        return Preference.prefs.getBoolean(LOCK_PREF_KEY, DEFAULT_LOCK);
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
        Preference.prefs.put(TEXT_PREF_KEY, text);
    }

    /**
     * getter of the text of the Note.
     */
    public static String getText() {
        return Preference.prefs.get(TEXT_PREF_KEY, DEFAULT_TEXT);
    }

    /**
     * Preference Change Listener
     * 
     * @param listener PreferenceChangeListener
     */
    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        Preference.prefs.addPreferenceChangeListener(listener);
    }
}
