package com.github.srilakshmikanthanp.quicknote.system

import com.github.srilakshmikanthanp.quicknote.consts.AppConsts
import com.github.srilakshmikanthanp.quicknote.editor.NoteEditor
import com.github.srilakshmikanthanp.quicknote.utility.Preference

import javafx.application.Platform
import javafx.scene.control.Alert

import java.awt.*
import java.awt.event.MouseEvent
import java.io.IOException
import java.net.URI
import javax.swing.ImageIcon
import javax.swing.SwingUtilities
import javax.swing.event.MouseInputAdapter

/**
 * System Tray Icon
 */
object SystrayIcon {
    // tray Engine
    private val sysTray: SystemTray = SystemTray.getSystemTray()

    // Tray Image
    private val trayIcon: TrayIcon = TrayIcon(ImageIcon(this.javaClass.getResource("/images/logo.png")).image)

    /**
     * Open the webPage in default Browser
     */
    private fun openAppHomePage() {
        try {
            Desktop.getDesktop().browse(URI(AppConsts.APP_HOME))
        } catch (exp: IOException) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.contentText = "Cannot open webpage"
            alert.showAndWait()
        }
    }

    /**
     * Inverts the NoteEditor
     * @param x location-x
     * @param y location-y
     */
    private fun invertNoteEditor(x: Int, y: Int) {
        Platform.runLater {
            if (!NoteEditor.isShowing) {
                NoteEditor.show(x.toDouble(), y.toDouble())
            } else {
                NoteEditor.hide()
            }
        }
    }

    /**
     * Gets the PopupMenu for Tray
     */
    private fun getTrayPopupMenu(): PopupMenu {
        val popupMenu = PopupMenu()
        val about = MenuItem("About")
        val lock = CheckboxMenuItem("Lock")
        val exit = MenuItem("Exit")

        lock.state = Preference.isLocked()

        popupMenu.add(about)
        popupMenu.add(lock)
        popupMenu.addSeparator()
        popupMenu.add(exit)

        about.addActionListener {
            this.openAppHomePage()
        }
        lock.addActionListener {
            Preference.setLocked(lock.state)
        }
        exit.addActionListener {
            Platform.runLater(Platform::exit)
        }

        return popupMenu
    }

    /**
     * Initilizer Block
     */
    init {
        this.trayIcon.addMouseListener(object : MouseInputAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (SwingUtilities.isLeftMouseButton(e)) invertNoteEditor(e?.x ?: 0, e?.y ?: 0)
            }
        })

        this.trayIcon.isImageAutoSize = true
        this.trayIcon.popupMenu = getTrayPopupMenu()
    }

    /**
     * Add TrayIcon to System
     */
    fun addIconToSystem() = this.sysTray.add(trayIcon)

    /**
     * Removes icon from tray
     */
    fun removeIconFromSystem() = this.sysTray.remove(trayIcon)
}
