package com.github.srilakshmikanthanp.quicknote;

import javafx.application.*;
import javafx.stage.*;

import com.github.srilakshmikanthanp.quicknote.editor.*;
import com.github.srilakshmikanthanp.quicknote.system.*;
import com.github.srilakshmikanthanp.quicknote.utility.*;

/**
 * Quick Note Application
 */
public class QuickNote extends Application {
    // Note Editor
    private NoteEditor noteEditor = NoteEditor.getInstance();

    // System Stay
    private Systemtray tray = Systemtray.getInstance();

    /**
     * Initilize the Primary Stage
     * 
     * @param pStage
     */
    private void initPrimaryStage(Stage pStage) {
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setMaxWidth(0);
        pStage.setMaxHeight(0);
        pStage.setOpacity(0);
        pStage.setX(Double.MAX_VALUE);
        pStage.show();
    }

    /**
     * Initialize the Note Editor
     * 
     * @param pStage stage
     */
    private void initNoteEditor(Stage pStage) {
        noteEditor.initOwner(pStage);
        noteEditor.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * Initialize the System Tray
     * 
     * @param pStage stage
     */
    private void initSystemTray() {
        tray.setNoteEditor(noteEditor);
        tray.addToSystemTray();
    }

    /**
     * Stater
     */
    @Override
    public void start(Stage pStage) {
        this.initPrimaryStage(pStage);
        this.initNoteEditor(pStage);
        this.initSystemTray();
    }

    /**
     * stopper
     */
    @Override
    public void stop() {
        // Remove the Tray Icon
        tray.removeFromSystemTray();
    }

    /**
     * Main Function to start the application
     * 
     * @param args args
     */
    public static void main(String[] args) {
        // If app is not running
        if(!Utilityfuncs.isAppRunning()) {
            launch(args);
        }

        // exit the app
        System.exit(0);
    }
}
