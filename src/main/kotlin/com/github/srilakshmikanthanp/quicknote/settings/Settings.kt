package com.github.srilakshmikanthanp.quicknote.settings

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import java.util.prefs.Preferences
import java.util.prefs.PreferenceChangeListener

/**
 * Object that helps to store the App Settings
 */
object Settings {
    /**
    * Preference Engine from Standard Java API
    */
    private val prefEngine: Preferences = Preferences.userNodeForPackage(Settings.javaClass)


    /*******************************
    *   Width of the Editor       *
    ******************************/
    const val WIDTH_KEY = "WIDTH"

    /**
    * Width Getter from preference
    *
    * @return Height
    */
    fun getWidth() = prefEngine.getDouble(WIDTH_KEY, Constants.DEF_WIDTH)

    /**
    * Width Setter to preference
    *
    * @param width
    */
    fun setWidth(width: Double) = prefEngine.putDouble(WIDTH_KEY, width)


    /*******************************
    *   Height of the Editor      *
    ******************************/
    const val HEIGHT_KEY = "HEIGHT"

    /**
    * Height Getter from preference
    *
    * @return Height
    */
    fun getHeight() = prefEngine.getDouble(HEIGHT_KEY, Constants.DEF_HEIGHT)

    /**
    * Height Setter to preference
    *
    * @param height
    */
    fun setHeight(height:Double) = prefEngine.putDouble(HEIGHT_KEY, height)


    /******************************
    *  Dark MODE of the Editor   *
    *****************************/
    const val DARK_KEY = "IS_DARK"

    /**
    * Theme getter from preference
    *
    * @return true if dark else false
    */
    fun isDark() = prefEngine.getBoolean(DARK_KEY, Constants.IS_DARK)

    /**
    * Theme setter to Preference
    *
    * @param isDark
    */
    fun setDark(isDark:Boolean) = prefEngine.putBoolean(DARK_KEY, isDark)


    /***************************
    * Preference Listener     *
    **************************/

    /**
    * Add a preference Change Listener
    *
    * @param listener
    */
    fun addPreferenceListener(listener: PreferenceChangeListener) {
        prefEngine.addPreferenceChangeListener(listener)
    }
}
