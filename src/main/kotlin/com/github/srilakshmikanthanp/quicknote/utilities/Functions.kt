package com.github.srilakshmikanthanp.quicknote.utilities

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import java.io.IOException
import java.awt.Desktop
import java.net.URI
import java.net.ServerSocket
import javafx.scene.control.Alert

/**
 * Is Application is Aldersy Running
 * @return true if it is, false if not
 */
fun isApplicationAlreadyRunning(): Boolean = try {
    val socket = ServerSocket(Constants.APP_PORT)
    val thread = Thread { socket.close() }
    Runtime.getRuntime().addShutdownHook(thread)
    false
} catch (exp: IOException) {
    true
}

/**
 * Open the webPage in default Browser
 */
fun browseURI(url: String): Unit = try {
    Desktop.getDesktop().browse(URI(url))
} catch (exp: IOException) {
    val alert = Alert(Alert.AlertType.ERROR)
    alert.contentText = "Cannot open webpage"
    alert.showAndWait()
    Unit // return unit
}
