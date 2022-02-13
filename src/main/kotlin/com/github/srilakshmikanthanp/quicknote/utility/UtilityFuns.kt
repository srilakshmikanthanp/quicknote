package com.github.srilakshmikanthanp.quicknote.utility

import com.github.srilakshmikanthanp.quicknote.appconsts.AppConsts

import javafx.scene.Scene
import javafx.scene.control.Alert

import java.io.IOException
import java.net.ServerSocket

/**
 * Utility functions for the Application
 */
object UtilityFuns {
    /**
     * Changes the Theme of the Window
     * @param scene
     */
    fun syncTheme(scene: Scene): Scene {
        // clear the previoue theme
        scene.stylesheets.clear()

        // Assume user selects Light
        var cssSheet = "/styles/Light.css"

        // get the theme from prefs
        if (Preference.isDark()) {
            cssSheet = "/styles/Dark.css"
        }

        // get the styleSheet
        val styleSheet = UtilityFuns.javaClass.getResource(cssSheet)

        // set the Theme to scene
        if (styleSheet != null) {
            scene.stylesheets.add(styleSheet.toExternalForm())
        } else {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.contentText = "StyleSheet Not Found"
            alert.showAndWait()
        }

        // return
        return scene
    }

    /**
     * Is Application is Aldersy Running
     * @return true if it is, false if not
     */
    fun isApplicationIsRunning(): Boolean = try {
        val socket = ServerSocket(AppConsts.APP_PORT)
        Runtime.getRuntime().addShutdownHook(Thread { socket.close() })
        false
    } catch (exp: IOException) {
        true
    }
}
