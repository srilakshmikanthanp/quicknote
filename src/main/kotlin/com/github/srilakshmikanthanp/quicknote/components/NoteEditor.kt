package com.github.srilakshmikanthanp.quicknote.components

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Screen

class NoteEditor(insets: Insets = Insets(4.0)) : NoteStage(insets) {
    // Text Area For the Note Editor
    private val textArea: TextArea = TextArea()

    /**
     * Initilize the Editor
     */
    init {
        // size for stage
        this.minHeight  =   Constants.MIN_HEIGHT
        this.minWidth   =   Constants.MIN_WIDTH
        this.height     =   Constants.DEF_HEIGHT
        this.width      =   Constants.DEF_WIDTH
        this.maxHeight  =   Constants.MAX_HEIGHT
        this.maxWidth   =   Constants.MAX_WIDTH

        // scene part
        val content = BorderPane(this.textArea)
        val cabinet = StackPane(content)

        content.styleClass.add("content")
        content.padding = insets
        cabinet.styleClass.add("cabinet")

        this.scene = Scene(cabinet, Color.TRANSPARENT)

        // event handlers
        this.focusedProperty().addListener {
          _, isLost, _ -> if (isLost) this.hide()
        }
    }

    /**
    * Get the TextArea Of the Editor
    */
    fun getTextArea(): TextArea = this.textArea

    /**
     * Show the Editor on the Position
     * @param x position-x
     * @param y position-y
     */
    fun show(x: Double, y: Double) {
        val rect2d = Screen.getPrimary().visualBounds
        val scaleX = Screen.getPrimary().outputScaleX
        val scaleY = Screen.getPrimary().outputScaleY
        var pCalcX = x / scaleX - (this.width / 2)
        var pCalcY = y / scaleY - (this.height / 2)
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
