package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.utilities.*
import javafx.application.Application
import javax.swing.JOptionPane.showMessageDialog
import javax.swing.JOptionPane.INFORMATION_MESSAGE

/**
 * Main function of the Application
 */
fun main(args: Array<String>) {
    // Check weather the App is already running
    if (isApplicationAlreadyRunning()) {
        return showMessageDialog(null, "Already Running", "QuickNote", INFORMATION_MESSAGE)
    }

    // Start the Application
    Application.launch(QuickNote::class.java, *args)
}
