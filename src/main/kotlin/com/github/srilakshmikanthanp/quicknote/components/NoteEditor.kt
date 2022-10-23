package com.github.srilakshmikanthanp.quicknote.components

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color


class NoteEditor(insets: Insets = Insets(5.0)) : BaseStage(insets) {
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

        content.styleClass.add("note-content")
        content.styleClass.add("note-editor")
        cabinet.styleClass.add("note-cabinet")

        this.scene = Scene(cabinet, Color.TRANSPARENT)

        // event handlers
        this.focusedProperty().addListener {
          _, isLost, _ -> if (isLost) this.hide()
        }

        // textArea
        textArea.promptText = "Put your Text Here"
    }

    /**
    * Get the TextArea Of the Editor
    */
    fun getTextArea(): TextArea = this.textArea
}
