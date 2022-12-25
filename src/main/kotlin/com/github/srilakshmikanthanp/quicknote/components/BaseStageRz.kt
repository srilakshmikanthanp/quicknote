package com.github.srilakshmikanthanp.quicknote.components

import com.github.srilakshmikanthanp.quicknote.typing.Positions
import javafx.geometry.Insets
import javafx.scene.input.*
import javafx.scene.*
import javafx.stage.*
import javafx.stage.StageStyle


open class BaseStageRz(private val insets: Insets): Stage() {
    // The Resizing Position
    private var resizePos: Positions = Positions.NORMAL

    /**
     * checks weather height is in range
     * @param height
     */
    private fun isHeightInRange(height: Double) : Boolean {
        return height < this.maxHeight && height > this.minHeight
    }

    /**
     * Checks wheathe width is in range
     * @param width
     */
    private fun isWidthInRange(width: Double) : Boolean {
        return width < this.maxWidth && width > this.minWidth
    }

    /**
     * Detect the position of the cursor
     * @param evt Mouse Event
     * @return Position of the cursor
     */
    private fun detectPos(evt: MouseEvent): Positions {
        return if( !this.isResizable ) {
            Positions.NORMAL
        } else if (evt.y > this.height - insets.bottom) {
            Positions.BOTTOM
        } else if (evt.x < insets.left) {
            Positions.LEFT
        } else if (evt.y < insets.top) {
            Positions.TOP
        } else if (evt.x > this.width - insets.right) {
            Positions.RIGHT
        } else {
            Positions.NORMAL
        }
    }

    /**
     * Changes the Mouse Icon to indicate
     * @param evt Mouse Event
     */
    private fun changeIcon(evt: MouseEvent) {
        this.scene.cursor = when (detectPos(evt)) {
            Positions.RIGHT  -> Cursor.E_RESIZE
            Positions.BOTTOM -> Cursor.N_RESIZE
            Positions.LEFT   -> Cursor.W_RESIZE
            Positions.TOP    -> Cursor.S_RESIZE
            Positions.NORMAL -> Cursor.DEFAULT
        }
    }

    /**
     * Start to resize the window
     * @param evt Mouse Event
     */
    private fun resizeStart(evt: MouseEvent) {
        this.resizePos = detectPos(evt)
    }

    /**
     * Resize the window on left
     * @param evt Mouse Event
     */
    private fun resizeLeft(evt: MouseEvent) {
        val dx = evt.screenX - this.x
        val width = this.width - dx
        if (isWidthInRange(width)) {
            this.width = width
            this.x += dx
        }
    }

    /**
     * Resize the window on top
     * @param evt Mouse Event
     */
    private fun resizeTop(evt: MouseEvent) {
        val dy = evt.screenY - this.y
        val height = this.height - dy
        if (isHeightInRange(height)) {
            this.height = height
            this.y += dy
        }
    }

    /**
     * Resize the window on right
     * @param evt Mouse Event
     */
    private fun resizeRight(evt: MouseEvent) {
        val dx = evt.screenX - this.x - this.width
        val width = this.width + dx
        if (isWidthInRange(width)) {
            this.width = width
        }
    }

    /**
     * Resize the window on bottom
     * @param evt Mouse Event
     */
    private fun resizeBottom(evt: MouseEvent) {
        val dy = evt.screenY - this.y - this.height
        val height = this.height + dy
        if (isHeightInRange(height)) {
            this.height = height
        }
    }

    /**
     * Stop to resize the window
     * @param evt Mouse Event
     */
    private fun resizeStop(evt: MouseEvent) {
        this.resizePos = Positions.NORMAL
    }

    /**
     * Resize the Window while the drag
     * @param evt Mouse Event
     */
    private fun resize(evt: MouseEvent) {
        when (this.resizePos) {
            Positions.LEFT -> resizeLeft(evt)
            Positions.TOP -> resizeTop(evt)
            Positions.RIGHT -> resizeRight(evt)
            Positions.BOTTOM -> resizeBottom(evt)
            Positions.NORMAL -> return
        }
    }

    /**
     * Initilizer
     */
    init {
        // Event Handlers to the Scene
        this.sceneProperty().addListener { _, _, newScene ->
            newScene.setOnMousePressed(::resizeStart)
            newScene.setOnMouseDragged(::resize)
            newScene.setOnMouseReleased(::resizeStop)
            newScene.setOnMouseMoved(::changeIcon)
        }

        // Style and behaviour of stage
        this.initStyle(StageStyle.TRANSPARENT)
    }
}
