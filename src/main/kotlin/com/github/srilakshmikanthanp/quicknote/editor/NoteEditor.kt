package com.github.srilakshmikanthanp.quicknote.editor

import com.github.srilakshmikanthanp.quicknote.appconsts.AppConsts
import com.github.srilakshmikanthanp.quicknote.utility.Preference
import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns

import javafx.scene.control.*
import javafx.scene.input.*
import javafx.scene.layout.*
import javafx.scene.*
import javafx.stage.*
import javafx.scene.paint.Color

import java.io.PrintStream

/**
 * QuickNote Editor
 */
class NoteEditor : Stage() {
    /**
     * Saves the text to File with JavaFx
     * @param text String
     */
    private fun saveTextToFile(text: String) {
        // define the required values
        val chooseFile = FileChooser()
        val extFilters = FileChooser.ExtensionFilter("Text File", "*.txt")

        // init the file chooser
        chooseFile.title = "Save to File"
        chooseFile.initialFileName = "content"
        chooseFile.extensionFilters.add(extFilters)

        // get the selected file
        val file = chooseFile.showSaveDialog(this) ?: return

        // save the text to file
        val printer = PrintStream(file)
        printer.use { printer.print(text) }
    }

    /**
     * Initilize the Editor Secne
     */
    private fun initilizeStageScene() {
        // Initilize the Tezt Area
        val saver = KeyCombination.keyCombination("CTRL+S")
        val textArea = TextArea(Preference.getText())

        textArea.promptText = "Put your Text Here"
        textArea.textProperty().addListener { _, _, newText ->
            Preference.setText(newText)
        }
        textArea.setOnKeyPressed {
            if (saver.match(it)) saveTextToFile(textArea.text)
        }

        // Initilize the Panes
        val container = BorderPane(textArea)
        val stackPane = StackPane(container)

        container.id = "noteditor"
        container.styleClass.add("container")
        stackPane.styleClass.add("stackpane")

        // Initilize the Scene
        val theme = KeyCombination.keyCombination("ALT+T")
        val scene = Scene(stackPane)

        scene.fill = Color.TRANSPARENT
        scene.setOnKeyPressed {
            if (!theme.match(it)) return@setOnKeyPressed
            Preference.setDark(!Preference.isDark())
            UtilityFuns.syncTheme(this.scene)
        }

        // set theme and return
        this.scene = UtilityFuns.syncTheme(scene)
    }

    /**
     * Initilize the Stage Dimension
     */
    private fun initlizeStageDimension() {
        // minimum constrains
        this.minWidth = AppConsts.MIN_WIDTH
        this.minHeight = AppConsts.MIN_HEIGHT
        // default constrains
        this.width = Preference.getWidth()
        this.height = Preference.getHeight()
        // maximum constrins
        this.maxWidth = AppConsts.MAX_WIDTH
        this.maxHeight = AppConsts.MAX_HEIGHT
    }

    /**
     * Initilize the Handlers
     */
    private fun initilizeStageHandlers() {
        // add focus listenet to hide stage on focus lost
        this.focusedProperty().addListener { _, isLost, _ ->
            if (isLost && !Preference.isLocked()) hide()
        }
        // activity save the width of stage to prefs
        this.widthProperty().addListener { _, _, width ->
            Preference.setWidth(width.toDouble())
        }
        // activity save the height of stage to prefs
        this.heightProperty().addListener { _, _, height ->
            Preference.setHeight(height.toDouble())
        }
    }

    /**
     * Initilizer Block
     */
    init {
        this.isAlwaysOnTop = true
        this.initlizeStageDimension()
        this.initilizeStageScene()
        this.initilizeStageHandlers()
    }

    /**
     * Show the Editor on the Position
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

        // if x position is high or low
        if (pCalcX + this.width > rect2d.maxX) {
            pCalcX = rect2d.maxX - this.width - margin
        } else if (pCalcX < rect2d.minX) {
            pCalcX = rect2d.minX + margin
        }

        // if y position is high or low
        if (pCalcY + this.height > rect2d.maxY) {
            pCalcY = rect2d.maxY - this.height - margin
        } else if (pCalcY < rect2d.minY) {
            pCalcY = rect2d.minY + margin
        }

        // show the editor
        this.show()
        this.x = pCalcX
        this.y = pCalcY
    }
}