package com.github.srilakshmikanthanp.quicknote.utility

import com.github.srilakshmikanthanp.quicknote.appconsts.AppConsts

import javafx.scene.Scene
import javafx.scene.control.Alert
import java.awt.Desktop

import java.io.IOException
import java.net.ServerSocket
import java.net.URI

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
        var css = "/styles/Light.css"

        // get the theme from prefs
        if (Preference.isDark()) {
            css = "/styles/Dark.css"
        }

        // get the styleSheet
        val styleSheet = UtilityFuns.javaClass.getResource(css)

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
    fun isApplicationAlreadyRunning(): Boolean = try {
        val socket = ServerSocket(AppConsts.APP_PORT)
        val thread = Thread { socket.close() }
        Runtime.getRuntime().addShutdownHook(thread)
        false
    } catch (exp: IOException) {
        true
    }

    /**
     * Open the webPage in default Browser
     */
    fun browseURI(url: String) : Unit = try {
        Desktop.getDesktop().browse(URI(url))
    } catch (exp: IOException) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.contentText = "Cannot open webpage"
        alert.showAndWait()
        Unit // return unit
    }
}
