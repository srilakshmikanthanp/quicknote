// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.AppPane;

import java.util.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

/**
 * Panes Controller
 */
class Navigator extends TreeView<String> {
    /**
     * Navigation Header
     */
    final String navigation = "Navigation";

    /**
     * get the root node
     */
    private TreeItem<String> getNavTreeItems(List<String> navStrings) {
        // create the root node
        TreeItem<String> root = new TreeItem<>(this.navigation);

        // add childrens to root node
        for (String navString : navStrings) {
            root.getChildren().add(new TreeItem<>(navString));
        }

        // init
        root.setExpanded(true);
        this.setPadding(new Insets(10));

        // return the root node
        return root;
    }

    /**
     * Constructor
     */
    public Navigator(List<String> navStrings) {
        this.setShowRoot(false);
        this.setRoot(this.getNavTreeItems(navStrings));
    }
}

/**
 * Panes Controller
 */
public class MainPane extends BorderPane {
    /**
     * position tracker
     */
    double offx = 0, offy = 0;
    
    /**
     * Inner Border PAne
     */
    final BorderPane innerPane = new BorderPane();

    /**
     * Available navigation sections
     */
    final List<String> navSections = Arrays.asList(
        PaneAbout.navTitle, PanePrefs.navTitle, PaneTerms.navTitle
    );

    /**
     * Sections
     */
    final PaneAbout paneAbout = new PaneAbout();
    final PanePrefs panePrefs = new PanePrefs();
    final PaneTerms paneTerms = new PaneTerms();

    /**
     * Xchange the New Pane
     * 
     * @param newVal name
     */
    private void setSection(String newVal) {
        switch (newVal) {
            case PaneAbout.navTitle:
                this.innerPane.setCenter(this.paneAbout);
                break;
            case PanePrefs.navTitle:
                this.innerPane.setCenter(this.panePrefs);
                break;
            case PaneTerms.navTitle:
                this.innerPane.setCenter(this.paneTerms);
                break;
        }
    }

    /**
     * get the navigation tree
     * @return Node
     */
    private Node getNavigator() {
        var title = new Label("QuickNote");
        var imageView = new ImageView();
        var nav = new Navigator(this.navSections);
        var pane = new BorderPane();

        // init title
        imageView.setImage(
            new Image(MainPane.class.getResourceAsStream("/images/logo.png"))
        );
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        title.setPadding(new Insets(5, 10, 15, 10));
        title.setGraphic(imageView);
        title.setContentDisplay(ContentDisplay.LEFT);

        // inti nav
        nav.getSelectionModel().select(0);
        nav.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                this.setSection(newValue.getValue());
            }
        );

        // init pane
        pane.setTop(title);
        pane.setCenter(nav);
        pane.setId("qnote-nav-bar");

        return pane;
    }

    /**
     * get the topbar
     */
    private Node getTopBar(Stage pStage) {
        var hideButton = new Button("X");
        hideButton.setOnAction(e -> {
            pStage.close();
        });
        hideButton.setId("qnote-hide-btn");

        var dragButton = new Button("");
        dragButton.setOpacity(0);
        dragButton.setMaxWidth(Double.MAX_VALUE);
        dragButton.setMaxHeight(Double.MAX_VALUE);
        dragButton.setOnMousePressed(evt -> {
            this.offx = pStage.getX() - evt.getScreenX();
            this.offy = pStage.getY() - evt.getScreenY();
        });
        dragButton.setOnMouseDragged(evt -> {
            pStage.setX(evt.getScreenX() + this.offx);
            pStage.setY(evt.getScreenY() + this.offy);
        });

        var topBar = new BorderPane();
        topBar.setRight(hideButton);
        topBar.setCenter(dragButton);
        return topBar;
    }

    /**
     * Constructr+or
     */
    public MainPane(Stage pStage) {
        // add navigation
        this.setLeft(this.getNavigator());
        this.innerPane.setTop(this.getTopBar(pStage));
        this.setSection(this.navSections.get(0));
        this.setCenter(this.innerPane);
    }
}
