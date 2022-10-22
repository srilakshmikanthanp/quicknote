package com.github.srilakshmikanthanp.quicknote.components

import com.github.srilakshmikanthanp.quicknote.constants.Constants
import javafx.stage.*

class NoteEditor: Stage() {
    /**
     * Show the Editor on the Position
     * @param x position-x
     * @param y position-y
     */
    private fun show(x: Double, y: Double) {
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

    /**
     * Initilize the Editor
     */
    init {
        // Style and behaviour of stage
        this.initStyle(StageStyle.TRANSPARENT)
        this.isAlwaysOnTop = true

        // size for stage
        this.minHeight  =   Constants.MIN_HEIGHT
        this.minWidth   =   Constants.MIN_WIDTH
        this.height     =   Constants.DEF_HEIGHT
        this.width      =   Constants.DEF_WIDTH
        this.maxHeight  =   Constants.MAX_HEIGHT
        this.maxWidth   =   Constants.MAX_WIDTH

        // scene part
    }

    /**
     * Invert the Showing status
     * @param x location-x
     * @param y location-y
     */
    public fun invert(x: Double, y: Double) {
        if (this.isShowing) {
            this.hide()
        } else {
            this.show(x, y)
        }
    }
}
