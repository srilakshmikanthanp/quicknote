// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.System;

import com.github.srilakshmikanthanp.quicknote.Utility.Prefs;

import javafx.application.*;
import javafx.stage.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.TrayIcon.MessageType;
import javax.swing.event.MouseInputAdapter;

/**
 * MouseListener
 */
class TrayIconMouseListener  extends MouseInputAdapter {
    /**
     * Stage of the editor
     */
    private Stage eStage;

    /**
     * ALternate the Editor stage
     */
    private void alternateStage() {
        if(eStage.isShowing()) {
            eStage.hide();
        } else {
            eStage.show();
        }
    }

    /**
     * Constructor
     * @param stage Stage of the editor
     */
    public TrayIconMouseListener(Stage stage) {
        this.eStage = stage;
    }

    /**
     * Listener for mouse click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Platform.runLater(
                () -> this.alternateStage()
            );
        }
    }
}

/**
 * Quick Note System Tray onle from awt thread
 */
public class Tray {
    /**
     * SystemTRay
     */
    private static SystemTray tray = SystemTray.getSystemTray();

    /**
     * TrayIcon
     */
    private TrayIcon trayIcon = new TrayIcon(
        new ImageIcon(getClass().getResource("/images/logo.png")).getImage(),
        "QuickNote"
    );

    /**
     * Lock Event
     */
    private void setLock(boolean state) {
        Prefs.setLockFocus(state);
    }

    /**
     * Exit Event
     */
    private void exit() {
        System.exit(0);
    }

    /**
     * Adds to SystemTray
     */
    private void addToTray() {
        // tryto add icon
        try {
            Tray.tray.add(this.trayIcon);
            this.trayIcon.displayMessage(
                "QuickNote", "QuickNote is Running Now !", MessageType.INFO
            );
        } catch(Exception e) {
            JOptionPane.showMessageDialog(
                null, "Can't add to tray", "QuickNote", JOptionPane.ERROR
            );
        }
    }

    /**
     * Tray
     */
    public Tray(Stage pStage, Stage eStage) {
        var popUpMenu = new PopupMenu();
        var qNote = new MenuItem("QuickNote");
        var lock = new CheckboxMenuItem("Lock");
        var exit = new MenuItem("Exit");

        // Add menu items to popUpMenu
        popUpMenu.add(qNote);
        popUpMenu.add(lock);
        popUpMenu.addSeparator();
        popUpMenu.add(exit);
        lock.setState(Prefs.getLockFocus());

        // add possible event listeners
        qNote.addActionListener(
            e -> Platform.runLater(() -> pStage.show())
        );
        lock.addItemListener(
            e -> Platform.runLater(() -> setLock(lock.getState()))
        );
        exit.addActionListener(
            e -> Platform.runLater(() -> exit())
        );
        this.trayIcon.addMouseListener(
            new TrayIconMouseListener(eStage)
        );

        // init tray
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setPopupMenu(popUpMenu);

        // Add to system tray
        this.addToTray();
    }
}
