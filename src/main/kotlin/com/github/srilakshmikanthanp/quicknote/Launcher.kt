package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns
import javafx.application.Application
import javafx.scene.control.Alert

/**
 * Main Method to startup the Application
 */
fun main(args: Array<String>) {
    if ( UtilityFuns.isApplicationAlreadyRunning() ) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "QuickNote"
        alert.contentText = "Already running"
        alert.showAndWait()
        return
    }

    Application.launch(QuickNote::class.java, *args)
}
