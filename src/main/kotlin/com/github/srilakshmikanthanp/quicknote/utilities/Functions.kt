package com.github.srilakshmikanthanp.quicknote.utilities

import com.github.srilakshmikanthanp.quicknote.constants.*
import javafx.scene.Scene
import java.awt.Desktop
import java.net.*
import javafx.scene.control.Alert

/**
 * Apply the Theme to Scene
 * @param dark is dark
 */
fun applyTheme(scene: Scene, dark: Boolean) {
    // get the styleSheet
    val styleSheet = object {}.javaClass.getResource(if (dark) {
        "/styles/Night.css"
    } else {
        "/styles/Light.css"
    })
    val sheets = scene.stylesheets

    // set the Theme to scene
    if (styleSheet != null) {
        sheets.clear(); sheets.add(styleSheet.toExternalForm())
        return
    }

    // Inform Error to user
    val alert = Alert(Alert.AlertType.ERROR)
    alert.contentText = "Please Report to Quicknote By clicking Issue"
    alert.title = "StyleSheet Not Found"
    alert.showAndWait()
}

/**
 * Is Application is Aldersy Running
 * @return true if it is, false if not
 */
fun isApplicationAlreadyRunning(): Boolean = try {
    val socket = ServerSocket(Constants.APP_PORT)
    val thread = Thread { socket.close() }
    Runtime.getRuntime().addShutdownHook(thread)
    false
} catch (exp: Exception) {
    true
}

/**
 * Open the webPage in default Browser
 */
fun browseURI(url: String): Unit = try {
    Desktop.getDesktop().browse(URI(url))
} catch (exp: Exception) {
    val alert = Alert(Alert.AlertType.ERROR)
    alert.contentText = "Cannot open webpage"
    alert.showAndWait()
    Unit // return unit
}
