// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.AppPane;

import java.io.IOException;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * About Pane 
 */
public class PaneAbout extends BorderPane {
    /**
     * Naitation Title
     */
    final static String navTitle = "About";

    /**
     * get the graphic image
     * 
     * @return ImageView
     */
    private ImageView getLogoImageView() {
        var image = new ImageView(
            new Image(getClass().getResourceAsStream("/images/logo.png"))
        );

        image.setFitHeight(100);
        image.setFitWidth(100);

        return image;
    }

    /**
     * Returns the Description
     * 
     * @return String
     */
    private String getBriefString() {
        final var properties = new Properties();
        
        try {
            properties.load(
                getClass().getResourceAsStream("/quicknote.properties")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        final var version = properties.getProperty("version");

        return  "Take your notes handy with QuickNote. " + 
                "QuickNote Helps you to Get Your Note " + 
                "handly in the taskbar Copy or Change " + 
                "it Whenever you want.\n" + 
                "\nVersion : " + version + "\n";
    }

    /**
     * Constructor
     */
    public PaneAbout() {
        // create the lable content
        Label breifLable = new Label();

        breifLable.setWrapText(true);
        breifLable.setMaxSize(450, 400);
        breifLable.setText(this.getBriefString());
        breifLable.setTextAlignment(TextAlignment.CENTER);
        breifLable.setGraphic(this.getLogoImageView());
        breifLable.setContentDisplay(ContentDisplay.TOP);

        // add it to the pane
        this.setPadding(new Insets(20));
        this.setCenter(breifLable);
    }
}
