package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.utilities.*
import javafx.scene.control.Alert
import javafx.application.Application

/**
* Main function of the Application
*/
fun main(args: Array<String>) {
    if ( isApplicationAlreadyRunning() ) {
        val alert           =   Alert(Alert.AlertType.WARNING)
        alert.contentText   =   "Already running"
        alert.title         =   "QuickNote"
        alert.showAndWait()
        return
    }

    val quicknote = QuickNote::class.java
    Application.launch(quicknote, *args)
}
