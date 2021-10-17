// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.System;

import com.github.srilakshmikanthanp.quicknote.Editor.*;
import com.github.srilakshmikanthanp.quicknote.Utility.*;

import javafx.application.*;
import javafx.stage.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.TrayIcon.MessageType;
import javax.swing.event.MouseInputAdapter;


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
     * Primary stage
     */
    private Stage pStage;

    /**
     * EDitor stage
     */
    private Editor eStage;

    /**
     * Show editor
     * @param x pos x
     * @param y pos y
     */
    private void showEditor(double x, double y) {
        Platform.runLater(() ->  {
            if(this.eStage.isShowing()) {
                this.eStage.hide();
            } else {
                this.eStage.showOnPosition(x, y);
            }
        });
    }

    /**
     * Show primary on Event Dispatch Thread
     */
    private void showPrimaryStage() {
        Platform.runLater(() -> {
            this.pStage.show();
            this.pStage.toFront();
        });
    }

    /**
     * Lock Event on Event Dispatch Thread
     */
    private void setLock(boolean state) {
        Platform.runLater(
            () -> Prefs.setLockFocus(state)
        );
    }

    /**
     * Exit Event on Event Dispatch Thread
     */
    private void exit() {
        Platform.runLater(
            () -> Platform.exit()
        );
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
    public Tray(Stage pStage, Editor eStage) {
        // have primary stage
        this.pStage = pStage;
        this.eStage = eStage;

        // create components
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
            e -> this.showPrimaryStage()
        );
        lock.addItemListener(
            e -> this.setLock(lock.getState())
        );
        exit.addActionListener(
            e -> this.exit()
        );
        this.trayIcon.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Tray.this.showEditor(e.getX(), e.getY());
                }
            }
        });

        // init tray
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setPopupMenu(popUpMenu);

        // Add to system tray
        this.addToTray();
    }
}
