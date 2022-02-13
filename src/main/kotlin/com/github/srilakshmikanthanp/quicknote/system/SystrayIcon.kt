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
object SystrayIcon : TrayIcon(ImageIcon(object {}.javaClass.getResource("/images/logo.png")).image) {
    // listeners
    private var clickListeners = listOf<(x: Double, y: Double) -> Unit>()

    /**
     * Notifies the Listsners
     */
    private fun notifyListsners(evt: MouseEvent) {
        for (listsner in clickListeners) {
            Platform.runLater { listsner(evt.x.toDouble(), evt.y.toDouble()) }
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
        lock.addItemListener { Preference.setLocked(lock.state) }
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

    /**
     * Add Click Listeners
     * @param lis (x:Double, y:Double) -> Unit
     */
    fun addClickListsner(lis: (x: Double, y: Double) -> Unit) {
        this.clickListeners += lis
    }

    /**
     * Remove the Click Listener
     * @param lis (x:Double, y:Double) -> Unit
     */
    fun removeClickListsnser(lis: (x: Double, y: Double) -> Unit) {
        this.clickListeners -= lis
    }
}
