package com.github.srilakshmikanthanp.quicknote.utility

import com.github.srilakshmikanthanp.quicknote.consts.AppConsts
import java.util.prefs.Preferences
import java.util.prefs.PreferenceChangeListener

/**
 * Object that helps to store the App Preference
 */
object Preference {
    /**
     * Preference Engine from Standard Java API
     */
    private val prefEngine: Preferences = Preferences.userNodeForPackage(Preference.javaClass)

    /*******************************
     *   Width of the Editor       *
     ******************************/
    const val PREF_Width_KEY = "HEIGHT"

    /**
     * Width Getter from preference
     *
     * @return Height
     */
    fun getWidth() = prefEngine.getDouble(PREF_Width_KEY, AppConsts.DEF_HEIGHT)

    /**
     * Width Setter to preference
     *
     * @param width
     */
    fun setWidth(width: Double) = prefEngine.putDouble(PREF_Width_KEY, width)

    /*******************************
     *   Height of the Editor      *
     ******************************/
    const val PREF_HEIGHT_KEY = "HEIGHT"

    /**
     * Height Getter from preference
     *
     * @return Height
     */
    fun getHeight() = prefEngine.getDouble(PREF_HEIGHT_KEY, AppConsts.DEF_HEIGHT)

    /**
     * Height Setter to preference
     *
     * @param height
     */
    fun setHeight(height:Double) = prefEngine.putDouble(PREF_HEIGHT_KEY, height)

    /******************************
     *  Dark MODE of the Editor   *
     *****************************/
    const val PREF_DARK_KEY = "DARK_THEME"

    /**
     * Theme getter from preference
     *
     * @return true if dark else false
     */
    fun isDark() = prefEngine.getBoolean(PREF_DARK_KEY, AppConsts.DEF_THEME)

    /**
     * Theme setter to Preference
     *
     * @param isDark
     */
    fun setDark(isDark:Boolean) = prefEngine.putBoolean(PREF_DARK_KEY, isDark)

    /******************************
     *  Lock of the Editor        *
     *****************************/
    const val PREF_LOCK_KEY = "LOCKED"

    /**
     * Lock getter from preference
     *
     * @return lock
     */
    fun isLocked() = prefEngine.getBoolean(PREF_LOCK_KEY, AppConsts.DEF_LOCK)

    /**
     * Lock setter to preference
     *
     * @param isLocked
     */
    fun setLocked(isLocked:Boolean) = prefEngine.putBoolean(PREF_LOCK_KEY, isLocked)

    /*****************************
     * Text of the Editor        *
     ****************************/
    const val PREF_TEXT_KEY = "TEXT_VAL"

    /**
     * Text getter from preference
     *
     * @return text
     */
    fun getText() = prefEngine.get(PREF_TEXT_KEY, AppConsts.DEF_TEXT)

    /**
     * Text setter to preference
     *
     * @param text
     */
    fun setText(text:String) = prefEngine.put(PREF_TEXT_KEY, text)

    /***************************
     * Preference Listener     *
     **************************/

    /**
     * Add a preference Change Listener
     *
     * @param listsner
     */
    fun addPreferenceListener(listener: PreferenceChangeListener) = prefEngine.addPreferenceChangeListener(listener)
}
