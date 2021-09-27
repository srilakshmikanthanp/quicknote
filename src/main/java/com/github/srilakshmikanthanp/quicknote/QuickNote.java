// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote;

import com.github.srilakshmikanthanp.quicknote.AppPane.MainPane;
import com.github.srilakshmikanthanp.quicknote.Editor.Editor;
import com.github.srilakshmikanthanp.quicknote.System.Tray;
import com.github.srilakshmikanthanp.quicknote.Utility.Helper;
import com.github.srilakshmikanthanp.quicknote.Utility.Prefs;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javax.swing.SwingUtilities;

/**
 * Application
 */
public class QuickNote extends Application {
    /**
     * Override start method to launch the application
     */
    @Override
    public void start(Stage pStage) throws Exception {
        // init primary stage
        pStage.setTitle("QuickNote");
        pStage.setScene(new Scene(new MainPane()));
        pStage.setMinWidth(Helper.MIN_WIN_WIDTH);
        pStage.setMinHeight(Helper.MIN_WIN_HEIGHT);
        Helper.setTheme(pStage.getScene());
        pStage.getIcons().add(
            new Image(getClass().getResourceAsStream("/images/logo.png"))
        );

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
        launch(args);
    }
}
