package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.editor.NoteEditor
import com.github.srilakshmikanthanp.quicknote.system.SystrayIcon

import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.StageStyle

import java.awt.SystemTray

/**
 * QuickNote class is the Main class for
 * the Application
 */
class QuickNote : Application() {
    /**
     * Call back for the Application Startup
     * @param primaryStage stage
     */
    override fun start(primaryStage: Stage) {
        // initilize the Primary Stage
        primaryStage.initStyle(StageStyle.UTILITY)
        primaryStage.opacity    =    0.0
        primaryStage.maxWidth   =    0.0
        primaryStage.maxHeight  =    0.0
        primaryStage.x = Double.MAX_VALUE
        primaryStage.show()

        // Initilize the Note Editor
        val note: NoteEditor = NoteEditor()
        note.initOwner(primaryStage)

        // initilize TrayIcon
        SystrayIcon.addClickListsner(note::invert)
        SystemTray.getSystemTray().add(SystrayIcon)
    }

    /**
     * Call back for the Application Stop
     */
    override fun stop() {
        SystemTray.getSystemTray().remove(SystrayIcon)
    }
}
