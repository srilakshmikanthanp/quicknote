// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Editor;

import com.github.srilakshmikanthanp.quicknote.Utility.Helper;
import com.github.srilakshmikanthanp.quicknote.Utility.Prefs;
import com.github.srilakshmikanthanp.quicknote.Utility.Resizer;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

import java.io.*;

/**
 * Editor Pane
 */
public class Editor extends Stage {
    /**
     * get Empty Stage
     */
    private Stage getEmptyStage() {
        // stage
        var stage = new Stage();

        // for taskbar
        stage.initStyle(StageStyle.UTILITY);

        // size
        stage.setHeight(0);
        stage.setWidth(0);
        stage.setOpacity(0);

        // show
        stage.show();

        // return
        return stage;
    }

    /**
     * Save Action
     */
    private void saveToFile(String text) {
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
        if (selectedFile != null) {
            // stream
            PrintStream filePrintStream;

            // try to open
            try {
                filePrintStream = new PrintStream(selectedFile);
            } catch (FileNotFoundException e) {
                return;
            }

            // write to file
            filePrintStream.print(text);

            // close
            filePrintStream.close();
        }
    }

    /**
     * Update Preference
     */
    private void updatePrefs(String key) {
        switch (key) {
            case Prefs.HEIGHT_PREF_KEY:
                this.setHeight(Prefs.getHeight());
                break;
            case Prefs.WIDTH_PREF_KEY:
                this.setWidth(Prefs.getWidth());
                break;
            case Prefs.THEME_PREF_KEY:
                Helper.setTheme(this.getScene());
                break;
        }
    }

    /**
     * Constructor
     */
    public Editor() {
        // Stage Preerties
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(this.getEmptyStage());

        // create things
        var textArea = new TextArea(Prefs.getText());
        var ctrlShiftS = KeyCombination.keyCombination("Ctrl+Shift+S");
        var borderPane = new BorderPane();
        var scene = new Scene(borderPane);

        textArea.setOnKeyPressed(evt -> {
            if (ctrlShiftS.match(evt)) {
                this.saveToFile(textArea.getText());
            }
        });
        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            Prefs.setText(newVal);
        });
        textArea.getStyleClass().add("qnote-focus-color-none");
        textArea.setPromptText("Place your text here");
        textArea.setWrapText(true);

        borderPane.setId("qnote-editor");
        borderPane.setCenter(textArea);
        borderPane.setPadding(new Insets(10, 5, 5, 5));

        scene.setFill(Color.TRANSPARENT);

        // Focus Event
        this.focusedProperty().addListener((obs, isLost, isGain) -> {
            if (isLost && !Prefs.getLockFocus()) {
                this.hide();
            }
        });

        // dimension
        this.widthProperty().addListener(
            (obs, oldVal, newVal) -> {
                Prefs.setWidth(newVal.doubleValue());
            }
        );
        this.heightProperty().addListener(
            (obs, oldVal, newVal) -> {
                Prefs.setHeight(newVal.doubleValue());
            }
        );

        this.setScene(scene);
        this.setWidth(Prefs.getWidth());
        this.setHeight(Prefs.getHeight());
        this.setMinWidth(Prefs.MIN_WIDTH);
        this.setMinHeight(Prefs.MIN_HEIGHT);
        this.setMaxWidth(Prefs.MAX_WIDTH);
        this.setMaxHeight(Prefs.MAX_HEIGHT);
        this.setAlwaysOnTop(true);
        
        Helper.setTheme(scene);

        new Resizer(this, 10, 5);

        // add Event Listener to preference
        Prefs.prefs.addPreferenceChangeListener(e -> {
            Platform.runLater(() -> this.updatePrefs(e.getKey()));
        });
    }

    /**
     * Show on position from the mouse event
     */
    public void showOnPosition(double x, double y) {
        var rect2D = Screen.getPrimary().getVisualBounds();
        var scaleX = Screen.getPrimary().getOutputScaleX();
        var scaleY = Screen.getPrimary().getOutputScaleY();
        var margin = 15;
        var pCalcX = x / scaleX - (this.getWidth() / 2);
        var pCalcY = y / scaleY - (this.getHeight() / 2);

        if (pCalcX < rect2D.getMinX()) {
            pCalcX = rect2D.getMinX() + margin;
        } else if (pCalcX + this.getWidth() > rect2D.getMaxX()) {
            pCalcX = rect2D.getMaxX() - this.getWidth() - margin;
        }

        if (pCalcY < rect2D.getMinY()) {
            pCalcY = rect2D.getMinY() + margin;
        } else if (pCalcY + this.getHeight() > rect2D.getMaxY()) {
            pCalcY = rect2D.getMaxY() - this.getHeight() - margin;
        }

        this.show();
        this.setX(pCalcX);
        this.setY(pCalcY);
    }
}
