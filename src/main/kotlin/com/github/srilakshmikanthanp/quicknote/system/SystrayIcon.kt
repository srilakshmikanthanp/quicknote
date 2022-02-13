package com.github.srilakshmikanthanp.quicknote.system

import com.github.srilakshmikanthanp.quicknote.appconsts.AppConsts
import com.github.srilakshmikanthanp.quicknote.utility.Preference
import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns
import javafx.application.Platform
import java.awt.CheckboxMenuItem
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.TrayIcon
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import javax.swing.SwingUtilities

/**
 * System Tray Icon
 */
object SystrayIcon : TrayIcon(ImageIcon(SystrayIcon.javaClass.getResource("/images/logo.png")).image) {
    // interface for listining click event
    fun interface ClickListeners {
        fun mouseClicked(x: Int, y: Int)
    }

    // listeners
    private val clickListeners = listOf<ClickListeners>()

    /**
     * Notifies the Listsners
     */
    private fun notifyListsners(evt: MouseEvent) {
        for (listsner in clickListeners) {
            listsner.mouseClicked(evt.x, evt.y)
        }
    }

    /**
     * Gets the PopupMenu for Tray
     */
    private fun initilizeTrayPopupMenu() {
        val popupMenu = PopupMenu()
        val about = MenuItem("About")
        val lock = CheckboxMenuItem("Lock")
        val exit = MenuItem("Exit")

        lock.state = Preference.isLocked()

        popupMenu.add(about)
        popupMenu.add(lock)
        popupMenu.addSeparator()
        popupMenu.add(exit)

        about.addActionListener { UtilityFuns.browseURI(AppConsts.APP_HOME) }
        lock.addActionListener { Preference.setLocked(lock.state) }
        exit.addActionListener { Platform.runLater(Platform::exit) }

        this.popupMenu = popupMenu
        this.isImageAutoSize = true
    }

    /**
     * Initilizer Block
     */
    init {
        val mouseListsner = object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                if (SwingUtilities.isLeftMouseButton(evt)) notifyListsners(evt)
            }
        }

        this.initilizeTrayPopupMenu()
        this.addMouseListener(mouseListsner)
    }
}
