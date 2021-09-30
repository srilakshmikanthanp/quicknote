// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote;

import com.github.srilakshmikanthanp.quicknote.AppPane.*;
import com.github.srilakshmikanthanp.quicknote.Editor.*;
import com.github.srilakshmikanthanp.quicknote.System.*;
import com.github.srilakshmikanthanp.quicknote.Utility.*;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.image.*;

import javax.swing.*;

/**
 * Application
 */
public class QuickNote extends Application {
    /**
     * make stage
     */
    private void stageInitilizer(Stage pStage) {
        // init primary stage
        pStage.initStyle(StageStyle.UNDECORATED);
        pStage.getIcons().add(
            new Image(getClass().getResource("/images/logo.png").toExternalForm())
        );

        // main pane
        var mainPane = new MainPane(pStage);

        // create scene
        var scene = new Scene(
            mainPane,  Helper.MIN_WIN_WIDTH, Helper.MIN_WIN_HEIGHT
        );

        pStage.setScene(scene);
    }

    /**
     * Override start method to launch the application
     */
    @Override
    public void start(Stage pStage) throws Exception {
        // init
        this.stageInitilizer(pStage);
        new Resizer(pStage, 10, 7);
        Helper.setTheme(pStage.getScene());
        pStage.setMinWidth(Helper.MIN_WIN_WIDTH);
        pStage.setMinHeight(Helper.MIN_WIN_HEIGHT);
        
        // init Editor stage
        var eStage =  new Editor();

        // init the tray
        SwingUtilities.invokeLater(() -> {
            new Tray(pStage, eStage);
        });

        // add Preference Event
        Prefs.prefs.addPreferenceChangeListener(e -> {
            if(e.getKey().equals(Prefs.THEME_PREF_KEY)) {
                Platform.runLater(
                    () -> Helper.setTheme(pStage.getScene())
                );
            }
        });
    }

    /**
     * Main method to start the Application
     */
    public static void main(String[] args) {
        // check if the application is already running
        if(Helper.isAppRunning()) {
            // print error message for debug
            System.err.println("Application is already running");
            // exit
            System.exit(0);
        }

        // launch
        launch(args);

        // exit
        System.exit(0);
    }
}
