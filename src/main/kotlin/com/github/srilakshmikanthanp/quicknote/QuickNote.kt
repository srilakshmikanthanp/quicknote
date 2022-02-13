package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.editor.NoteEditor
import com.github.srilakshmikanthanp.quicknote.system.SystrayIcon
import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.StageStyle

/**
 * QuickNote Application
 */
class QuickNote : Application() {
    /**
     * Initilize the Primary Stage
     */
    private fun initPrimaryStage(stage:Stage) {
        stage.initStyle(StageStyle.UTILITY)
        stage.maxWidth = 0.0
        stage.maxHeight = 0.0
        stage.opacity = 0.0
        stage.x = Double.MAX_VALUE
        stage.show()
    }

    /**
     * Initilize the Note Editor
     */
    private fun initNoteEditor(pStage:Stage) {
        NoteEditor.initOwner(pStage)
        NoteEditor.initStyle(StageStyle.TRANSPARENT)
    }

    /**
     * Initilize the System Tray
     */
    private fun initSystemTray() {
        SystrayIcon.addIconToSystem()
    }

    /**
     * Called on Application start by JavaFx
     */
    override fun start(primaryStage: Stage) {
        initPrimaryStage(primaryStage)
        initNoteEditor(primaryStage)
        initSystemTray()
    }

    /**
     * Called on Application stop by JavaFx
     */
    override fun stop() {
        SystrayIcon.removeIconFromSystem()
    }
}

/**
 * Main Method to startup
 */
fun main(args: Array<String>) {
    Application.launch(QuickNote::class.java, *args)
}
