package com.github.srilakshmikanthanp.quicknote.editor;

import java.io.*;

import javafx.application.Platform;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.quicknote.consts.*;
import com.github.srilakshmikanthanp.quicknote.utility.*;

/**
 * Note Editor For Application.
 */
public class NoteEditor extends Stage {
    // shortcut to save the text
    private static final String saveShortcut = "Ctrl+S";

    // singletone instance
    private static NoteEditor instance;

    /**
     * Update Preference for the Editor.
     * 
     * @param key Key of the Preference
     */
    private void preferenceChanged(String key) {
        if(key.equals(Preference.HEIGHT_KEY)) {
            this.setHeight(Preference.getHeight());
            return;
        }
        
        if(key.equals(Preference.WIDTH_KEY)) {
            this.setWidth(Preference.getWidth());
            return;
        }
        
        if(key.equals(Preference.THEME_KEY)) {
            Utilityfuncs.setTheme(this.getScene());
            return;
        }
    }

    /**
     * Save the text to File
     * 
     * @param text data
     */
    private void saveText(String text) {
        // file chooser
        var fileChooser = new FileChooser();

        // init
        fileChooser.setTitle("Save to File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text File", "*.txt")
        );

        // show and get the file
        var selectedFile = fileChooser.showSaveDialog(this);

        // attempt to save
        if (selectedFile == null) {
            return;
        }

        // stream
        PrintStream printer;

        // try to open
        try {
            printer = new PrintStream(selectedFile);
        } catch (FileNotFoundException e) {
            return;
        }

        // write to file
        printer.print(text);
        
        // close
        printer.close();
    }

    /**
     * Get the TextArea for Editor.
     * 
     * @return TextArea
     */
    private TextArea getTextArea() {
        // define the text area
        var ctrlShiftS = KeyCombination.keyCombination(saveShortcut);
        var textArea = new TextArea(Preference.getText());

        // set the shortcuts
        textArea.setOnKeyPressed(e -> {
            if (ctrlShiftS.match(e)) {
                this.saveText(textArea.getText());
            }
        });

        // set the text listener
        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
                Preference.setText(newVal);
        });

        // return the text area
        return textArea;
    }

    /**
     * get the Pane for Editor.
     * 
     * @return Pane
     */
    private Pane getPane() {
        // define
        var container = new BorderPane(this.getTextArea());
        var stackPane = new StackPane(container);

        // intialize
        stackPane.setPadding(new Insets(20));
        stackPane.getStyleClass().add("stackpane");
        container.getStyleClass().add("container");

        // return
        return stackPane;
    }

    /**
     * Constructor FOR Note Editor.
     * 
     * @param owner - Owner Stage.
     */
    private NoteEditor() {
        // create scene
        var scene = new Scene(this.getPane());

        // dimensions of app
        this.setWidth(Preference.getWidth());
        this.setHeight(Preference.getHeight());

        // min dimensions
        this.setMinWidth(AppConsts.MIN_WIDTH);
        this.setMinHeight(AppConsts.MIN_HEIGHT);

        // max dimensions
        this.setMaxWidth(AppConsts.MAX_WIDTH);
        this.setMaxHeight(AppConsts.MAX_HEIGHT);

        // initlize
        this.setScene(scene);
        this.setAlwaysOnTop(true);

        // add resizer
        AppResizer.addResizer(this, 10, 10);

        // add Event Listener to preference
        Preference.addPreferenceChangeListener(e -> {
            Platform.runLater(() -> this.preferenceChanged(e.getKey()));
        });

        // focus listener
        this.focusedProperty().addListener((obs, isLost, isGain) -> {
            if (isLost && !Preference.getLock()) this.hide();
        });

        // width listener
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            Preference.setWidth(newVal.doubleValue());
        });
        
        // height listener
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            Preference.setHeight(newVal.doubleValue());
        });
    }

    /**
     * Show on position from the mouse event
     */
    public void show(double x, double y) {
        // defien the vars
        var rect2D = Screen.getPrimary().getVisualBounds();
        var scaleX = Screen.getPrimary().getOutputScaleX();
        var scaleY = Screen.getPrimary().getOutputScaleY();
        var pCalcX = x / scaleX - (this.getWidth() / 2);
        var pCalcY = y / scaleY - (this.getHeight() / 2);
        var margin = 15;

        // calculate the position of x
        if (pCalcX < rect2D.getMinX()) {
            pCalcX = rect2D.getMinX() + margin;
        } else if (pCalcX + this.getWidth() > rect2D.getMaxX()) {
            pCalcX = rect2D.getMaxX() - this.getWidth() - margin;
        }

        // calculate the position of y
        if (pCalcY < rect2D.getMinY()) {
            pCalcY = rect2D.getMinY() + margin;
        } else if (pCalcY + this.getHeight() > rect2D.getMaxY()) {
            pCalcY = rect2D.getMaxY() - this.getHeight() - margin;
        }

        // set visible
        this.show();
        this.setX(pCalcX);
        this.setY(pCalcY);
    }

    /**
     * Get Note Editor Instance.
     * 
     * @param owner - Owner Stage.
     * @return NoteEditor Instance.
     */
    public static NoteEditor getInstance() {
        if (instance == null) {
            instance = new NoteEditor();
        }
        return instance;
    }
}
