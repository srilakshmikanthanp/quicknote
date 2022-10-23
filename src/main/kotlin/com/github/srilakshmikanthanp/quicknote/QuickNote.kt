package com.github.srilakshmikanthanp.quicknote

import com.github.srilakshmikanthanp.quicknote.components.NoteEditor
import com.github.srilakshmikanthanp.quicknote.taskbar.NoteIcon
import com.github.srilakshmikanthanp.quicknote.interfaces.TextStore
import com.github.srilakshmikanthanp.quicknote.settings.Settings
import com.github.srilakshmikanthanp.quicknote.storage.FileStore
import com.github.srilakshmikanthanp.quicknote.utilities.applyTheme
import java.awt.SystemTray
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import javafx.stage.StageStyle

class QuickNote : Application() {
    // Theme change Switch
    private val themeSwitch : KeyCombination    =   KeyCombination.keyCombination("ALT+T")

    // Note Editor
    private val noteEditor  : NoteEditor        =   NoteEditor()

    // Text Store
    private val textStore   : TextStore         =   FileStore

    /**
     * SystemTray Mouse event
     */
    private fun trayHandler(x: Double, y: Double) {
        if (noteEditor.isShowing) {
            noteEditor.hide()
        } else {
            noteEditor.show(x, y)
        }
    }

    /**
     * Initilize Block
     */
    init {
        // Settings Event Listeners (Only For Theme)
        Settings.addPreferenceListener { evt ->
            if (evt.key == Settings.DARK_KEY) {
                Platform.runLater { applyTheme(noteEditor.scene, Settings.isDark()) }
            }
        }


        // NoteEditor Event Handlers
        noteEditor.getTextArea().textProperty().addListener { _, _, newVal ->
            textStore.setText(newVal)
        }

        noteEditor.heightProperty().addListener { _, _, newVal ->
            Settings.setHeight(newVal.toDouble())
        }

        noteEditor.widthProperty().addListener { _, _, newVal ->
            Settings.setWidth(newVal.toDouble())
        }

        noteEditor.scene.setOnKeyPressed {
            if (themeSwitch.match(it)) {
                Settings.setDark(!Settings.isDark())
            }
        }


        // Set Initial Values
        applyTheme(this.noteEditor.scene, Settings.isDark())
        noteEditor.width = Settings.getWidth()
        noteEditor.height = Settings.getHeight()
        noteEditor.getTextArea().text = textStore.getText()
    }

    /**
     * Start of the Application
     */
    override fun start(primaryStage: Stage) {
        // initilize the Primary Stage
        primaryStage.initStyle(StageStyle.UTILITY)
        primaryStage.maxHeight = 0.0
        primaryStage.opacity = 0.0
        primaryStage.maxWidth = 0.0
        primaryStage.x = Double.MAX_VALUE
        primaryStage.show()

        // initilize noteeditor
        noteEditor.initOwner(primaryStage)

        // NoteIcon
        val trayIcon = SystemTray.getSystemTray()
        trayIcon.add(NoteIcon)
        NoteIcon.addListsner(::trayHandler)
    }

    /**
     * End of Application
     */
    override fun stop() {
        val trayIcon = SystemTray.getSystemTray()
        trayIcon.remove(NoteIcon)
    }
}
