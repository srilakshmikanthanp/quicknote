package com.github.srilakshmikanthanp.quicknote.editor

import com.github.srilakshmikanthanp.quicknote.appconsts.AppConsts
import com.github.srilakshmikanthanp.quicknote.utility.Preference
import com.github.srilakshmikanthanp.quicknote.utility.UtilityFuns
import javafx.geometry.Insets
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCombination
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.PrintStream
import kotlin.math.ceil

/**
 * QuickNote Editor Component
 * @param insets Insets
 */
class NoteEditor(private val insets: Insets = Insets(4.0)) : Stage() {
    /************************************
     *      Class States                *
     ***********************************/

    /**
     * Positions of the cursor
     */
    private enum class Positions { TOP, BOTTOM, LEFT, RIGHT, NORMAL }

    // The Start Position
    private var resizePosition: Positions = Positions.NORMAL

    /************************************
     *      Utility functions           *
     ***********************************/

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


    /************************************
     *      Resizer Functions           *
     ***********************************/


    /**
     * Detect the position of the cursor
     * @param evt Mouse Event
     * @return Position of the cursor
     */
    private fun detectCursorPosition(evt: MouseEvent): Positions {
        return if (evt.y > this.height - insets.bottom) {
            Positions.BOTTOM // In bottom Resize Position
        } else if (evt.x < insets.left) {
            Positions.LEFT   // In left Resize Position
        } else if (evt.y < insets.top) {
            Positions.TOP    // In top Resize Position
        } else if (evt.x > this.width - insets.right) {
            Positions.RIGHT  // In right Resize Position
        } else {
            Positions.NORMAL // In Normal Position
        }
    }

    /**
     * Changes the Mouse Icon to indicate
     * @param evt Mouse Event
     */
    private fun changeCursorIcon(evt: MouseEvent) {
        when (detectCursorPosition(evt)) {
            Positions.LEFT -> this.scene.cursor = Cursor.W_RESIZE
            Positions.TOP -> this.scene.cursor = Cursor.S_RESIZE
            Positions.RIGHT -> this.scene.cursor = Cursor.E_RESIZE
            Positions.BOTTOM -> this.scene.cursor = Cursor.N_RESIZE
            Positions.NORMAL -> this.scene.cursor = Cursor.DEFAULT
        }
    }

    /**
     * Start to resize the window
     * @param evt Mouse Event
     */
    private fun resizeStart(evt: MouseEvent) {
        resizePosition = detectCursorPosition(evt)
    }

    /**
     * Resize the window on left
     * @param evt Mouse Event
     */
    private fun resizeLeft(evt: MouseEvent) {
        val dx = evt.screenX - this.x
        val width = this.width - dx
        if (width < this.maxWidth && width > this.minWidth) {
            this.x += dx
            this.width = width
        }
    }

    /**
     * Resize the window on top
     * @param evt Mouse Event
     */
    private fun resizeTop(evt: MouseEvent) {
        val dy = evt.screenY - this.y
        val height = this.height - dy
        if (height < this.maxHeight && height > this.minHeight) {
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
        if (width < this.maxWidth && width > this.minWidth) {
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
        if (height < this.maxHeight && height > this.minHeight) {
            this.height = height
        }
    }

    /**
     * Resize the Window while the drag
     * @param evt Mouse Event
     */
    private fun resizeWindow(evt: MouseEvent) {
        when (resizePosition) {
            Positions.NORMAL -> return
            Positions.LEFT -> resizeLeft(evt)
            Positions.TOP -> resizeTop(evt)
            Positions.RIGHT -> resizeRight(evt)
            Positions.BOTTOM -> resizeBottom(evt)
        }
    }

    /**
     * Stop to resize the window
     * @param evt Mouse Event
     */
    private fun resizeStop(evt: MouseEvent) {
        resizePosition = Positions.NORMAL
    }

    /************************************
     *      Initilizer Functions        *
     ***********************************/

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
        container.padding = insets
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

        // set theme and scene
        this.scene = UtilityFuns.syncTheme(scene)
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

        // add mouse listener to handle mouse move event
        this.scene.setOnMouseMoved(this::changeCursorIcon)
        this.scene.setOnMousePressed(this::resizeStart)
        this.scene.setOnMouseDragged(this::resizeWindow)
        this.scene.setOnMouseReleased(this::resizeStop)
    }


    /**
     * Initilizer Block
     */
    init {
        this.isAlwaysOnTop = true
        this.initStyle(StageStyle.TRANSPARENT)
        this.initlizeStageDimension()
        this.initilizeStageScene()
        this.initilizeStageHandlers()
    }

    /**
     * Invert the Showing status
     * @param x location-x
     * @param y location-y
     */
    fun invert(x: Double, y: Double) {
        if (this.isShowing) {
            this.hide()
        } else {
            this.show(x, y)
        }
    }
}
