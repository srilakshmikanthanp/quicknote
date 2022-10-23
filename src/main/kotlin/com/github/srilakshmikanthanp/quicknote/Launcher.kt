package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.utilities.isApplicationAlreadyRunning
import javafx.application.Application
import javafx.scene.control.Alert

/**
* Main function of the Application
*/
fun main(args: Array<String>) {
    if ( isApplicationAlreadyRunning() ) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "QuickNote"
        alert.contentText = "Already running"
        alert.showAndWait()
        return
    }

    val quicknote = QuickNote::class.java
    Application.launch(quicknote, *args)
}
