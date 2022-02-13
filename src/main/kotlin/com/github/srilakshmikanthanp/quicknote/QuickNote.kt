package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.editor.NoteEditor
import com.github.srilakshmikanthanp.quicknote.system.SystrayIcon
import com.github.srilakshmikanthanp.quicknote.utility.StageSizer

import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.StageStyle

import java.awt.SystemTray

/**
 * QuickNote Application
 */
class QuickNote : Application() {
    /**
     * Call back for the Application Startup
     * @param primaryStage stage
     */
    override fun start(primaryStage: Stage) {
        // initilize the Primary Stage
        primaryStage.initStyle(StageStyle.UTILITY)
        primaryStage.maxWidth = 0.0
        primaryStage.maxHeight = 0.0
        primaryStage.opacity = 0.0
        primaryStage.x = 0.0
        primaryStage.show()

        // Note Editor Instance
        val noteEditor = NoteEditor()
        StageSizer.addResizer(noteEditor, 15, 15)
        noteEditor.initOwner(primaryStage)
        noteEditor.initStyle(StageStyle.TRANSPARENT)

        // Add listsner to icon
        SystrayIcon.addClickListsner { x, y ->
            if(!noteEditor.isShowing) {
                noteEditor.show(x.toDouble(), y.toDouble())
            } else {
                noteEditor.hide()
            }
        }

        // Add to system Tray
        SystemTray.getSystemTray().add(SystrayIcon)
    }

    /**
     * Call back for the Application Stop
     */
    override fun stop() {
        SystemTray.getSystemTray().remove(SystrayIcon)
    }
}
