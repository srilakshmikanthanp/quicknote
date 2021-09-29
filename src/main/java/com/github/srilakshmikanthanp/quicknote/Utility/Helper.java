// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.Utility;

import javafx.scene.Scene;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Helper Class
 */
public class Helper {
    /**
     * Minumum width of window
     */
    public static final double MIN_WIN_WIDTH = 800;

    /**
     * Minimum height of window
     */
    public static final double MIN_WIN_HEIGHT = 650;

    /**
     * Is application is running
     */
    public static boolean isAppRunning() {
        try {
            // check if port is available
            ServerSocket socket = new ServerSocket(57428);

            // close the socket on stop
            Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }, "SocketCloser")
            );

            // if port is available, not running
            return false;
        
        } catch (IOException e) {

            // if port is not available, running
            return true;
        }
    }

    /**
     * set theme to scene
     */
    public static void setTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(
            Helper.class.getResource(
                "/styles/" + Prefs.getTheme() + ".css"
            ).toExternalForm()
        );
    }
}
