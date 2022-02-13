package com.github.srilakshmikanthanp.quicknote.utility

import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.input.MouseEvent
import javafx.stage.Stage

/**
 *  _____ _                 _    _
 * |_   _| |__   __ _ _ __ | | _(_)_ __   __ _
 *   | | | '_ \ / _` | '_ \| |/ / | '_ \ / _` |
 *   | | | | | | (_| | | | |   <| | | | | (_| |
 *   |_| |_| |_|\__,_|_| |_|_|\_\_|_| |_|\__, |
 *                                      |___/
 *   @Simonwep for resizer this source is available at
 *   https://gist.github.com/Simonwep/642587d0e307de6da6347ba56f396231
 *   that is modified as needed.
 *   @author Simon Reinisch
 *   @version 0.0.2
 */
class StageSizer private constructor(stage: Stage, dt: Int, private val TR: Int) {
    private val listeners = HashMap<Cursor, EventHandler<MouseEvent>>()
    private val stage: Stage
    private val scene: Scene
    private val tm: Int
    private var mPresSceneX = 0.0
    private var mPresSceneY = 0.0
    private var mPresScreeX = 0.0
    private var mPresScreeY = 0.0
    private var mPresStageW = 0.0
    private var mPresStageH = 0.0

    /**
     * Create an FXResizeHelper for undecoreated JavaFX Stages. The only wich is
     * your job is to create an padding for the Stage so the user can resize it.
     */
    init {
        tm = dt + TR
        this.stage = stage
        scene = stage.scene
    }

    /**
     * Creates the Listener for the Stage.
     */
    private fun createListener() {
        listeners[Cursor.NW_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW - (event.screenX - mPresScreeX)
            val newHeight = mPresStageH - (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) {
                stage.y = event.screenY - mPresSceneY
                stage.height = newHeight
            }
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) {
                stage.x = event.screenX - mPresSceneX
                stage.width = newWidth
            }
        }
        listeners[Cursor.NE_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW - (event.screenX - mPresScreeX)
            val newHeight = mPresStageH + (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) stage.height = newHeight
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) {
                stage.x = event.screenX - mPresSceneX
                stage.width = newWidth
            }
        }
        listeners[Cursor.SW_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW + (event.screenX - mPresScreeX)
            val newHeight = mPresStageH - (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) {
                stage.height = newHeight
                stage.y = event.screenY - mPresSceneY
            }
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) stage.width = newWidth
        }
        listeners[Cursor.SE_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW + (event.screenX - mPresScreeX)
            val newHeight = mPresStageH + (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) stage.height = newHeight
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) stage.width = newWidth
        }
        listeners[Cursor.E_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW - (event.screenX - mPresScreeX)
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) {
                stage.x = event.screenX - mPresSceneX
                stage.width = newWidth
            }
        }
        listeners[Cursor.W_RESIZE] = EventHandler { event: MouseEvent ->
            val newWidth = mPresStageW + (event.screenX - mPresScreeX)
            if (newWidth > stage.minWidth && newWidth < stage.maxWidth) stage.width = newWidth
        }
        listeners[Cursor.N_RESIZE] = EventHandler { event: MouseEvent ->
            val newHeight = mPresStageH - (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) {
                stage.y = event.screenY - mPresSceneY
                stage.height = newHeight
            }
        }
        listeners[Cursor.S_RESIZE] = EventHandler { event: MouseEvent ->
            val newHeight = mPresStageH + (event.screenY - mPresScreeY)
            if (newHeight > stage.minHeight && newHeight < stage.maxHeight) stage.height = newHeight
        }
        listeners[Cursor.OPEN_HAND] = EventHandler { event: MouseEvent ->
            stage.x = event.screenX - mPresSceneX
            stage.y = event.screenY - mPresSceneY
        }
    }

    /**
     * Launch the Resizer.
     */
    private fun launch() {
        scene.onMousePressed = EventHandler { event: MouseEvent ->
            mPresSceneX = event.sceneX
            mPresSceneY = event.sceneY
            mPresScreeX = event.screenX
            mPresScreeY = event.screenY
            mPresStageW = stage.width
            mPresStageH = stage.height
        }
        scene.onMouseMoved = EventHandler { event: MouseEvent ->
            val sx = event.sceneX
            val sy = event.sceneY
            val lTrigger = sx > 0 && sx < TR
            val rTrigger = sx < scene.width && sx > scene.width - TR
            val uTrigger = sy < scene.height && sy > scene.height - TR
            val dTrigger = sy > 0 && sy < TR

            if (lTrigger && dTrigger) fireAction(Cursor.NW_RESIZE)
            else if (lTrigger && uTrigger) fireAction(Cursor.NE_RESIZE)
            else if (rTrigger && dTrigger) fireAction(Cursor.SW_RESIZE)
            else if (rTrigger && uTrigger) fireAction(Cursor.SE_RESIZE)
            else if (lTrigger) fireAction(Cursor.E_RESIZE)
            else if (rTrigger) fireAction(Cursor.W_RESIZE)
            else if (dTrigger) fireAction(Cursor.N_RESIZE)
            else if (sy < tm && !uTrigger) fireAction(Cursor.OPEN_HAND)
            else if (uTrigger) fireAction(Cursor.S_RESIZE)
            else fireAction(Cursor.DEFAULT)
        }
    }

    /**
     * Fires the action associated with the cursor.
     *
     * @param c the cursor
     */
    private fun fireAction(c: Cursor) {
        scene.cursor = c
        if (c !== Cursor.DEFAULT) scene.onMouseDragged = listeners[c] else scene.onMouseDragged = null
    }

    companion object {
        /**
         * Create an FXResizeHelper for undecoreated JavaFX Stages. The only wich is
         * your job is to create an padding for the Stage so the user can resize it.
         *
         * @param stage - The JavaFX Stage.
         */
        fun addResizer(stage: Stage, dt: Int, rt: Int) {
            val resizer = StageSizer(stage, dt, rt)
            resizer.createListener()
            resizer.launch()
        }
    }
}
