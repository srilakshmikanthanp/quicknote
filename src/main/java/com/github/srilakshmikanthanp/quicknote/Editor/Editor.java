// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Editor;

import com.github.srilakshmikanthanp.quicknote.Utility.Helper;
import com.github.srilakshmikanthanp.quicknote.Utility.Prefs;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.io.*;

/**
 * Editor Pane
 */
public class Editor extends Stage {
    /**
     * Offset for drag Purpose
     */
    private double offx = 0, offy = 0;

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
     * get draggble
     */
    private Node getDraggble() {
        // controls
        var dLabel = new Label("•••");
        var button = new Button();
        var pStack = new StackPane();

        // init
        button.setOpacity(0);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);

        // add event listener
        button.setOnMousePressed(evt -> {
            this.offx = this.getX() - evt.getScreenX();
            this.offy = this.getY() - evt.getScreenY();
        });

        button.setOnMouseDragged(evt -> {
            this.setX(evt.getScreenX() + this.offx);
            this.setY(evt.getScreenY() + this.offy);
        });

        // add to pane
        pStack.getChildren().addAll(
            dLabel,
            button
        );

        // return
        return pStack;
    }

    /**
     * Save Action
     */
    private void saveToFile(String text) {
        // file chooser
        var fileChooser = new FileChooser();

        // init
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
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
                this.hide();
                this.setHeight(Prefs.getHeight());
                break;
            case Prefs.WIDTH_PREF_KEY:
                this.hide();
                this.setWidth(Prefs.getWidth());
                break;
            case Prefs.THEME_PREF_KEY:
                this.hide();
                Helper.setTheme(this.getScene());
                break;
        }
    }

    /**
     * Constructor
     */
    public Editor() {
        // Stage Preerties
        this.initStyle(StageStyle.UNDECORATED);
        this.initOwner(this.getEmptyStage());

        // create things
        var textArea = new TextArea(Prefs.getText());
        var ctrlS = KeyCombination.keyCombination("Ctrl+S");
        var draggble = this.getDraggble();
        var borderPane = new BorderPane();
        var scene = new Scene(borderPane);

        textArea.setOnKeyPressed(evt -> {
            if (ctrlS.match(evt)) {
                this.saveToFile(textArea.getText());
            }
        });
        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            Prefs.setText(newVal);
        });
        textArea.getStyleClass().add("qnote-focus-color-none");
        textArea.setPadding(new Insets(6, 6, 0, 6));
        textArea.setPromptText("Place your text here");
        textArea.setWrapText(true);

        borderPane.setId("qnote-editor");
        borderPane.setCenter(textArea);
        borderPane.setBottom(draggble);

        // Focus Event
        this.focusedProperty().addListener((obs, isLost, isGain) -> {
            if (isLost && !Prefs.getLockFocus()) {
                this.hide();
            }
        });

        this.setScene(scene);
        this.setWidth(Prefs.getWidth());
        this.setHeight(Prefs.getHeight());
        this.setAlwaysOnTop(true);
        Helper.setTheme(scene);

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
        var pCalcX = x / scaleX - (this.getWidth() / 2);
        var pCalcY = y / scaleY - (this.getHeight() / 2);

        if (pCalcX < rect2D.getMinX()) {
            pCalcX = rect2D.getMinX();
        } else if (pCalcX + this.getWidth() > rect2D.getMaxX()) {
            pCalcX = rect2D.getMaxX() - this.getWidth();
        }

        if (pCalcY < rect2D.getMinY()) {
            pCalcY = rect2D.getMinY();
        } else if (pCalcY + this.getHeight() > rect2D.getMaxY()) {
            pCalcY = rect2D.getMaxY() - this.getHeight();
        }

        this.show();
        this.setX(pCalcX);
        this.setY(pCalcY);
    }
}
