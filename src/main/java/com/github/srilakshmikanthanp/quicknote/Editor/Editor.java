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

/**
 * Empty Stage uset as owner for Editor Stage
 */
class EmptyStage extends Stage {
    /**
     * Constructor
     */
    public EmptyStage() {
        // for taskbar
        this.initStyle(StageStyle.UTILITY);

        // size
        this.setHeight(0);
        this.setWidth(0);
        this.setOpacity(0);

        // show
        this.show();
    }
}

/**
 * A Botton and Lable
 */
class Draggble extends StackPane {
    /**
     * Button
     */
    private Button btn = new Button();

    /**
     * Label
     */
    private Label lbl = new Label("•••");

    /**
     * Constructor
     */
    public Draggble() {
        btn.setOpacity(0);
        btn.setMaxWidth(Double.MAX_VALUE);
        lbl.setAlignment(Pos.CENTER);
        lbl.setMaxWidth(Double.MAX_VALUE);

        this.getChildren().addAll(lbl, btn);
    }

    /**
     * getter of button
     */
    public Button getButton() {
        return this.btn;
    }
}

/**
 * Editor Pane
 */
public class Editor extends Stage {
    /**
     * Offset for drag Purpose
     */
    private double offx = 0, offy = 0;

    /**
     * Update Preference
     */
    private void updatePrefs(String key) {
        var rect2D = Screen.getPrimary().getVisualBounds();
        switch (key) {
            case Prefs.HEIGHT_PREF_KEY:
                this.setHeight(Prefs.getHeight());
                this.setY(rect2D.getHeight() - this.getHeight());
                break;
            case Prefs.WIDTH_PREF_KEY:
                this.setWidth(Prefs.getWidth());
                this.setX(rect2D.getWidth() - this.getWidth());
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
        this.initStyle(StageStyle.UNDECORATED);
        this.initOwner(new EmptyStage());

        // create things
        var textArea = new TextArea(Prefs.getText());
        var draggble = new Draggble();
        var borderPane = new BorderPane();
        var scene = new Scene(borderPane);

        // add Text Change Event
        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            Prefs.setText(newVal);
        });

        // add drag Listener
        draggble.getButton().setOnMousePressed(evt -> {
            this.offx = this.getX() - evt.getScreenX();
            this.offy = this.getY() - evt.getScreenY();
        });
        draggble.getButton().setOnMouseDragged(evt -> {
            this.setX(evt.getScreenX() + this.offx);
            this.setY(evt.getScreenY() + this.offy);
        });

        // place items
        textArea.setPadding(new Insets(6, 6, 0, 6));
        textArea.setPromptText("Place your text here");
        textArea.setWrapText(true);
        textArea.setStyle("-fx-background-color: transperent; -fx-font-size: 14px");
        draggble.setCursor(Cursor.CLOSED_HAND);
        borderPane.setCenter(textArea);
        borderPane.setBottom(draggble);

        // Focus Event
        this.focusedProperty().addListener(
            (obs, isLost, isGain) -> {
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
            Platform.runLater(
                () -> this.updatePrefs(e.getKey())
            );
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
        
        if(pCalcX < rect2D.getMinX()) {
            pCalcX = rect2D.getMinX();
        } else if(pCalcX + this.getWidth() > rect2D.getMaxX()) {
            pCalcX = rect2D.getMaxX() - this.getWidth();
        }

        if(pCalcY < rect2D.getMinY()) {
            pCalcY = rect2D.getMinY();
        } else if(pCalcY + this.getHeight() > rect2D.getMaxY()) {
            pCalcY = rect2D.getMaxY() - this.getHeight();
        }

        this.show();
        this.setX(pCalcX);
        this.setY(pCalcY);
    }
}
