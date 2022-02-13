package com.github.srilakshmikanthanp.quicknote.editor

import com.github.srilakshmikanthanp.quicknote.consts.AppConsts
import com.github.srilakshmikanthanp.quicknote.utility.Preference
import com.github.srilakshmikanthanp.quicknote.utility.StageSizer
import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns

import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.*
import javafx.stage.*
import javafx.scene.input.KeyCombination
import javafx.scene.paint.Color

import java.io.FileNotFoundException
import java.io.PrintStream

/**
 * Note Editor for the Application
 */
object NoteEditor : Stage() {
    // save shortcut string
    private const val saveShortCut = "CTRL+S"

    // theme change shortcut
    private const val modeShortCut = "ALT+T"

    /**
     * Saves the Text to File
     * @param text
     */
    private fun saveTextToFile(text: String){
        // define the required values
        val chooseFile = FileChooser()
        val extFilters = FileChooser.ExtensionFilter(
            "Text Files", "*.txt"
        )

        // init the file chooser
        chooseFile.title = "Save to File"
        chooseFile.initialFileName = "content.txt"
        chooseFile.extensionFilters.add(extFilters)

        // get the selected file
        val file = chooseFile.showSaveDialog(this) ?: return

        // try to save the text
        try {
            val printer = PrintStream(file)
            printer.use { printer.print(text) }
        } catch (exp: FileNotFoundException) {
            return
        }
    }

    /**
     * Handler for the Preference Change
     * @param prefKey
     */
    private fun preferenceChanged(prefKey: String) {
        when (prefKey) {
            Preference.WIDTH_KEY -> this.width = Preference.getWidth()
            Preference.HEIGHT_KEY -> this.height = Preference.getHeight()
            Preference.DARK_KEY -> UtilityFuns.syncTheme(this.scene)
        }
    }

    /**
     * Returns the Text area from Javafx
     * @return TextArea
     */
    private fun getTextArea(): TextArea {
        val saver = KeyCombination.keyCombination(saveShortCut)
        val textArea = TextArea(Preference.getText())

        textArea.promptText = "Put your Text Here"
        textArea.textProperty().addListener { _, _, newText ->
            Preference.setText(newText)
        }
        textArea.setOnKeyPressed {
            if (saver.match(it)) saveTextToFile(textArea.text)
        }

        return textArea
    }

    /**
     * Return the Editor Pane from Javafx
     * @return Pane
     */
    private fun getEditorPane(): Pane {
        // define the panes
        val container = BorderPane(getTextArea())
        val stackPane = StackPane(container)

        container.id = "noteditor"
        container.styleClass.add("container")
        stackPane.styleClass.add("stackpane")

        return stackPane
    }

    /**
     * Return the Scene from the JavaFx
     * @return Scene
     */
    private fun getEditorScene(): Scene {
        val theme = KeyCombination.keyCombination(modeShortCut)
        val scene = Scene(getEditorPane())

        scene.fill = Color.TRANSPARENT
        scene.setOnKeyPressed {
            if (theme.match(it)) {
                Preference.setDark(!Preference.isDark())
            }
        }

        return UtilityFuns.syncTheme(scene)
    }

    /**
     * Adds the Listeners to Editor
     */
    private fun addListenersAndHandlers() {
        this.focusedProperty().addListener { _, isLost, _ ->
            if (isLost && !Preference.isLocked()) hide()
        }

        this.widthProperty().addListener { _, _, width ->
            Preference.setWidth(width.toDouble())
        }

        this.heightProperty().addListener { _, _, height ->
            Preference.setHeight(height.toDouble())
        }

        Preference.addPreferenceListener {
            this.preferenceChanged(it.key)
        }
    }

    /**
     * Initilizer Block
     */
    init {
        this.width = Preference.getWidth()
        this.height = Preference.getHeight()

        this.minWidth = AppConsts.MIN_WIDTH
        this.minHeight = AppConsts.MIN_HEIGHT

        this.maxWidth = AppConsts.MAX_WIDTH
        this.maxHeight = AppConsts.MAX_HEIGHT

        this.scene = getEditorScene()
        this.isAlwaysOnTop = true

        this.addListenersAndHandlers()

        StageSizer.addResizer(this, 15, 15)
    }

    /**
     * Show the Editor on the Position
     *
     * @param x position-x
     * @param y position-y
     */
    fun show(x: Double, y: Double) {
        val rect2d = Screen.getPrimary().visualBounds
        val scaleX = Screen.getPrimary().outputScaleX
        val scaleY = Screen.getPrimary().outputScaleY
        var pCalcX = x / scaleX - (this.height / 2)
        var pCalcY = y / scaleY - (this.width / 2)
        val margin = 15

        // if x position is low or high
        if (pCalcX < rect2d.minX) {
            pCalcX = rect2d.minX + margin
        } else if (pCalcX + this.width > rect2d.maxX) {
            pCalcX = rect2d.maxX - this.width - margin
        }

        // if y position is low or high
        if (pCalcY < rect2d.minY) {
            pCalcY = rect2d.minY + margin
        } else if (pCalcY + this.height > rect2d.maxY) {
            pCalcY = rect2d.maxY - this.height - margin
        }

        // show the editor
        this.show()
        this.x = pCalcX
        this.y = pCalcY
    }
}
