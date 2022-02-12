package com.github.srilakshmikanthanp.quicknote.editor

import com.github.srilakshmikanthanp.quicknote.utility.Preference
import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns
import javafx.stage.FileChooser
import javafx.stage.Screen
import javafx.stage.Stage
import java.io.FileNotFoundException
import java.io.PrintStream

/**
 * Note Editor for the Appliction
 */
object NoteEditor : Stage() {
    // save shortcut string
    private const val saveShortCut = "CTRL+S"

    // theme change shorcut
    private const val modeShortCut = "ALT+T"

    /**
     * Handler for the Preference Change
     *
     * @param key
     */
    private fun preferenceChanged(key: String) {
        when (key) {
            Preference.WIDTH_KEY -> this.width = Preference.getWidth()
            Preference.HEIGHT_KEY -> this.height = Preference.getHeight()
            Preference.DARK_KEY -> UtilityFuns.syncTheme(this.scene)
        }
    }

    /**
     * Saves the Text to File
     *
     * @param text
     */
    private fun saveTextToFile(text: String) {
        // define
        val fileChoose = FileChooser()
        val extFilters = FileChooser.ExtensionFilter("Text Files", "*.txt")

        // init
        fileChoose.title = "Save to File"
        fileChoose.initialFileName = "quicknote.txt"
        fileChoose.extensionFilters.add(extFilters)

        // get the file
        val selectedFile = fileChoose.showSaveDialog(this)

        // try to save the text
        selectedFile?.let {
            try {
                val printer = PrintStream(selectedFile)
                printer.use { it.print(text) }
            } catch (exp: FileNotFoundException) {
                return;
            }
        }
    }

    /**
     * Initilizer Block
     */
    init {

    }

    /**
     * Show the Editor on the Position
     *
     * @param x position-x
     * @param y position-y
     */
    public fun show(x: Double, y: Double) {
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
        this.y = pCalcX
    }
}
