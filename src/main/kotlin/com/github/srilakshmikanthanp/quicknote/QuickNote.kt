package com.github.srilakshmikanthanp.quicknote


import com.github.srilakshmikanthanp.quicknote.constants.Constants
import com.github.srilakshmikanthanp.quicknote.taskbar.NoteIcon
import com.github.srilakshmikanthanp.quicknote.components.NoteEditor
import com.github.srilakshmikanthanp.quicknote.interfaces.TextStore
import com.github.srilakshmikanthanp.quicknote.settings.Settings
import com.github.srilakshmikanthanp.quicknote.storage.FileStore
import java.awt.SystemTray
import javafx.application.Application
import javafx.scene.control.Alert
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import javafx.stage.StageStyle

class QuickNote : Application() {
    // Theme change Switch
    private val themeSwitch : KeyCombination  =  KeyCombination.keyCombination("ALT+T")

    // Note Editor
    private val noteEditor  : NoteEditor      =  NoteEditor()

    // Text Store
    private val textStore   : TextStore       =  FileStore

    // Light css
    private val lightCss    : String          =  "/styles/Light.css"

    // Dark css
    private val darkCss     : String          =  "/styles/Night.css"

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
     * Apply the Theme to Scene
     */
    private fun applyTheme(dark: Boolean) {
        // get the styleSheet
        val styleSheet = object{}.javaClass.getResource(if (dark) darkCss else lightCss)
        val sheets = noteEditor.scene.stylesheets

        // set the Theme to scene
        if (styleSheet != null) {
            sheets.clear(); sheets.add(styleSheet.toExternalForm())
        }

        // Inform Error to user
        val alert = Alert(Alert.AlertType.ERROR)
        alert.contentText = "Please Report to ${Constants.APP_ISSUE_PAGE}"
        alert.title = "StyleSheet Not Found"
        alert.showAndWait()
    }

    /**
     * Initilize Block
     */
    init {
        // Settings Event Listeners (Only For Theme)
        Settings.addPreferenceListener { evt ->
            if(evt.key == Settings.DARK_KEY) { this.applyTheme(Settings.isDark()) }
        }

        noteEditor.scene.setOnKeyPressed {
            if (themeSwitch.match(it)) { Settings.setDark(!Settings.isDark()) }
        }

        // NoteEditor Event Handlers
        noteEditor.heightProperty().addListener {
            _, _, newVal -> Settings.setHeight(newVal.toDouble())
        }

        noteEditor.widthProperty().addListener {
            _, _, newVal -> Settings.setWidth(newVal.toDouble())
        }

        noteEditor.getTextArea().textProperty().addListener {
            _, _, newVal -> textStore.setText(newVal)
        }

        // Set Initial Values
        noteEditor.getTextArea().text = textStore.getText()
        noteEditor.width = Settings.getWidth()
        noteEditor.height = Settings.getHeight()
        this.applyTheme(Settings.isDark())
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
