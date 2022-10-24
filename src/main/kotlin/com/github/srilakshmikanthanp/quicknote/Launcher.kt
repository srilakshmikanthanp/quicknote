package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.utilities.*
import javafx.application.Application
import javax.swing.JOptionPane

/**
 * Main function of the Application
 */
fun main(args: Array<String>) {
    // Check weather the App is already running
    if (isApplicationAlreadyRunning()) {
        return JOptionPane.showMessageDialog(null, "Already Running")
    }

    // Start the Application
    val quicknote = QuickNote::class.java
    Application.launch(quicknote, *args)
}
