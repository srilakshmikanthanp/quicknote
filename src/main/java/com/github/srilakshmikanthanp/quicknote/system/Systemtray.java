package com.github.srilakshmikanthanp.quicknote.system;

import java.awt.*;
import java.net.URI;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import javafx.application.Platform;

import com.github.srilakshmikanthanp.quicknote.consts.AppConsts;
import com.github.srilakshmikanthanp.quicknote.editor.NoteEditor;
import com.github.srilakshmikanthanp.quicknote.utility.Preference;


/**
 * Quick Note System Tray only from awt thread
 */
public class Systemtray {
    // System Tray Instance to set
    private static SystemTray tray = SystemTray.getSystemTray();

    // singleton instance
    private static Systemtray instance = null;

    // Tray Icon for Quick Note
    private final TrayIcon trayIcon = new TrayIcon(
        new ImageIcon(getClass().getResource(
            "/images/logo.png"
        )).getImage(), "QuickNote"
    );

    // Note Editor Stage
    private NoteEditor noteEditor;

    /**
     * Open the url in browser
     * 
     * @param url url to open
     */
    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show editor
     * @param x pos x
     * @param y pos y
     */
    private void showEditor(double x, double y) {
        Platform.runLater(() ->  {
            if(noteEditor.isShowing()) {
                noteEditor.hide();
            } else {
                noteEditor.show(x, y);
            }
        });
    }

    /**
     * Gets the Pop Up Menu for the Tray Icon
     */
    private PopupMenu getPopupMenu() {
        var popUpMenu = new PopupMenu();
        var about = new MenuItem("About");
        var lock = new CheckboxMenuItem("Lock");
        var exit = new MenuItem("Exit");

        // Init the Lock Checkbox
        lock.setState(Preference.getLock());

        // Add menu items to popUpMenu
        popUpMenu.add(about);
        popUpMenu.add(lock);
        popUpMenu.addSeparator();
        popUpMenu.add(exit);

        // about listener
        about.addActionListener(e -> {
            this.openWebPage(AppConsts.APP_LINK);
        });

        // lock listener
        lock.addItemListener(e -> {
            Preference.setLock(lock.getState());
        });

        // exit listener
        exit.addActionListener(e -> {
            Platform.runLater(Platform::exit);
        });

        // return the popUpMenu
        return popUpMenu;
    }

    /**
     * Constructor
     */
    private Systemtray() {
        // Add Mouse Listener
        this.trayIcon.addMouseListener(new MouseInputAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showEditor(e.getX(), e.getY());
                }
            }
        });

        // Set the Pop Up Menu
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setPopupMenu(this.getPopupMenu());
    }

    /**
     * Add to System Tray
     */
    public void addToSystemTray() {
        try {
            tray.add(this.trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove from System Tray
     */
    public void removeFromSystemTray() {
        tray.remove(this.trayIcon);
    }

    /**
     * Note Editor setter
     */
    public void setNoteEditor(NoteEditor noteEditor) {
        this.noteEditor = noteEditor;
    }

    /**
     * Note Editor Getter
     */
    public NoteEditor getNoteEditor() {
        return this.noteEditor;
    }

    /**
     * Gets the Systemtray instance
     * 
     * @return Systemtray instance
     */
    public static Systemtray getInstance() {
        if (instance == null) {
            instance = new Systemtray();
        }
        return instance;
    }
}
