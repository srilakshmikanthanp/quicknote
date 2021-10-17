// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.AppPane;

import com.github.srilakshmikanthanp.quicknote.Utility.*;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.*;

/**
 * Preference PAne
 */
public class PanePrefs extends BorderPane {
    /**
     * Navigation Title
     */
    final static String navTitle = "Preference";

        /**
     * Geh the Height Pane
     */
    private VBox getContentPane() {
        // Width
        var widthLabel = new Label(
            "Set Width"
        );
        var minWidthLable = new Label(
            Prefs.MIN_WIDTH + ""
        );
        var widthSlider = new Slider (
            Prefs.MIN_WIDTH, 
            Prefs.MAX_WIDTH,
            Prefs.getWidth()
        );
        var maxWidthLable = new Label(
            Prefs.MAX_WIDTH + ""
        );
        var widthPane = new HBox(
            5,
            minWidthLable,
            widthSlider,
            maxWidthLable
        );

        widthLabel.setGraphic(widthPane);
        widthLabel.setContentDisplay(ContentDisplay.BOTTOM);

        // Height
        var heightLabel = new Label(
            "Set Height"
        );
        var minHeightLable = new Label(
            Prefs.MIN_HEIGHT + ""
        );
        var heightSlider = new Slider (
            Prefs.MIN_HEIGHT, 
            Prefs.MAX_HEIGHT,
            Prefs.getHeight()
        );
        var maxHeightLable = new Label(
            Prefs.MAX_HEIGHT + ""
        );
        var heightPane = new HBox(
            5,
            minHeightLable,
            heightSlider,
            maxHeightLable
        );

        heightLabel.setGraphic(heightPane);
        heightLabel.setContentDisplay(ContentDisplay.BOTTOM);

        // Dimension
        var dimensionPane = new VBox(
            20,
            widthLabel,
            heightLabel
        );
        var dimTitlePane = new TitledPane(
            "Dimension",
            dimensionPane
        );

        dimTitlePane.setCollapsible(false);
        dimTitlePane.setExpanded(true);
        dimTitlePane.setPadding(new Insets(20));

        // Theme Prefs
        var themeLable = new Label(
            "Select Theme    "
        );
        var themesList = new ChoiceBox<String>(
            FXCollections.observableArrayList(
                Prefs.LIGHT_THEME,
                Prefs.DARK_THEME
            )
        );

        themeLable.setGraphic(themesList);
        themeLable.setContentDisplay(ContentDisplay.RIGHT);
        themesList.setValue(Prefs.getTheme());

        var themePane = new VBox(
            5,
            themeLable
        );
        var themeTitlepane = new TitledPane(
            "Theme",
            themePane
        );

        themeTitlepane.setCollapsible(false);
        themeTitlepane.setExpanded(true);
        themeTitlepane.setPadding(new Insets(20));

        // Actions
        var resetButton = new Button(
            "Reset"
        );
        var saveButton = new Button(
            "Save"
        );

        resetButton.setOnAction(e -> {
            Prefs.setWidth(Prefs.DEFAULT_WIDTH);
            Prefs.setHeight(Prefs.DEFAULT_HEIGHT);
            Prefs.setTheme(Prefs.DEFAULT_THEME);
        });
        saveButton.setOnAction(e -> {
            Prefs.setWidth(widthSlider.getValue());
            Prefs.setHeight(heightSlider.getValue());
            Prefs.setTheme(themesList.getValue());
        });

        var buttonsPane = new HBox(
            20,
            resetButton,
            saveButton
        );

        buttonsPane.setAlignment(Pos.CENTER_RIGHT);
        buttonsPane.setPadding(new Insets(20));

        // Final Pane
        var contentPane = new VBox(
            5,
            dimTitlePane,
            themeTitlepane,
            buttonsPane
        );

        // add prrference listsner
        Prefs.prefs.addPreferenceChangeListener(e -> {
            Platform.runLater(() -> {
                switch(e.getKey()) {
                    case Prefs.HEIGHT_PREF_KEY:
                        heightSlider.setValue(Prefs.getHeight());
                        break;
                    case Prefs.WIDTH_PREF_KEY:
                        widthSlider.setValue(Prefs.getWidth());
                        break;
                    case Prefs.THEME_PREF_KEY:
                        themesList.setValue(Prefs.getTheme());
                        break;
                }
            });
        });

        // Done
        return contentPane;
    }
  
    /**
     * Dimension of the box
     */
    public PanePrefs() {
        this.setPadding(new Insets(20));
        this.setCenter(this.getContentPane());
    }
}
