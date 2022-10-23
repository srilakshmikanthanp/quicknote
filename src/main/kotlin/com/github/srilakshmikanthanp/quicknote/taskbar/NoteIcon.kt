package com.github.srilakshmikanthanp.quicknote.taskbar

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import com.github.srilakshmikanthanp.quicknote.utilities.browseURI
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.TrayIcon
import javafx.application.Platform
import javax.swing.ImageIcon
import javax.swing.SwingUtilities

/**
* System Tray Icon
*/
object NoteIcon : TrayIcon(ImageIcon(object {}.javaClass.getResource("/images/logo.png")).image) {
    // listeners
    private var clickListeners = listOf<(x: Double, y: Double) -> Unit>()

    /**
    * Notifies the Listsners
    */
    private fun notify(evt: MouseEvent) {
        this.clickListeners.forEach {
            Platform.runLater { it(evt.xOnScreen.toDouble(), evt.yOnScreen.toDouble()) }
        }
    }

    /**
    * Initilizer Block
    */
    init {
        val mouseListsner = object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                if (SwingUtilities.isLeftMouseButton(evt)) notify(evt)
            }
        }

        val popupMenu = PopupMenu()
        val about = MenuItem("About")
        val issue = MenuItem("Issue")
        val exit = MenuItem("Exit")

        popupMenu.add(about)
        popupMenu.add(issue)
        popupMenu.addSeparator()
        popupMenu.add(exit)

        about.addActionListener { browseURI(Constants.APP_HOME_PAGE) }
        issue.addActionListener { browseURI(Constants.APP_ISSUE_PAGE) }
        exit.addActionListener { Platform.runLater(Platform::exit) }

        this.popupMenu = popupMenu
        this.isImageAutoSize = true

        this.addMouseListener(mouseListsner)
    }

    /**
    * Add Click Listeners
    * @param lis (x:Double, y:Double) -> Unit
    */
    fun addListsner(lis: (x: Double, y: Double) -> Unit) {
        this.clickListeners += lis
    }

    /**
    * Remove the Click Listener
    * @param lis (x:Double, y:Double) -> Unit
    */
    fun removeListsnser(lis: (x: Double, y: Double) -> Unit) {
        this.clickListeners -= lis
    }
}
