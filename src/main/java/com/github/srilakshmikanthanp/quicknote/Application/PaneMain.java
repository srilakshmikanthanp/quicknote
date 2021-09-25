// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Application;

import java.util.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

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

        // set expanded
        root.setExpanded(true);

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
class PaneMain extends BorderPane {
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
                this.setCenter(this.paneAbout);
                break;
            case PanePrefs.navTitle:
                this.setCenter(this.panePrefs);
                break;
            case PaneTerms.navTitle:
                this.setCenter(this.paneTerms);
                break;
        }
    }

    /**
     * Constructr+or
     */
    public PaneMain() {
        // create the navigation bar
        var navigator = new Navigator(this.navSections);

        // add navigation
        this.setLeft(navigator);

        // add default section
        navigator.getSelectionModel().select(0);
        this.setCenter(this.paneAbout);

        // eventListener
        navigator.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                this.setSection(newValue.getValue());
        });
    }
}
