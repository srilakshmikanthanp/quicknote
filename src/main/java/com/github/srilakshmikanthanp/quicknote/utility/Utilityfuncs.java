package com.github.srilakshmikanthanp.quicknote.utility;

import java.io.IOException;
import java.net.ServerSocket;
import javafx.scene.*;

/**
 * Utility functions for the application.
 */
public class Utilityfuncs {
  /**
   * set theme to scene
   */
  public static void setTheme(Scene scene) {
    // clear
    scene.getStylesheets().clear();

    // initial theme
    var css = "/styles/Light.css";

    // check theme
    if (Preference.getTheme().equals(Preference.DARK_THEME)) {
      css = "/styles/Dark.css";
    }

    // set css
    scene.getStylesheets().add(
      Utilityfuncs.class.getResource(css).toExternalForm()
    );
  }

  /**
   * Is application is running
   */
  public static boolean isAppRunning() {
    // detect the status of the application
    try {
      // check if port is available
      ServerSocket socket = new ServerSocket(57428);

      // close the socket on stop
      Runtime.getRuntime().addShutdownHook(
          new Thread(() -> {
            try { socket.close(); } catch (IOException e) {}
          }, "SocketCloser"));

      // if port is available, not running
      return false;
    } catch (IOException e) {
      // if port is not available, running
      return true;
    }
  }
}
