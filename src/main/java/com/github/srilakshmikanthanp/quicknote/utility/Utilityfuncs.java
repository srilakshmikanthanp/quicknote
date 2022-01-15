package com.github.srilakshmikanthanp.quicknote.utility;

import java.io.IOException;
import java.net.ServerSocket;
import javafx.scene.*;
import jfxtras.styles.jmetro.*;

/**
 * Utility functions for the application.
 */
public class Utilityfuncs {
  /**
   * set theme to scene
   */
  public static void setTheme(Scene scene) {
    // initial theme
    var style = Style.LIGHT;
    var css = "/styles/Light.css";

    // check theme
    if (Preference.getTheme().equals(Preference.DARK_THEME)) {
      style = Style.DARK;
      css = "/styles/Dark.css";
    }

    // craate jmetro
    JMetro jMetro = new JMetro(style);

    // set scene
    jMetro.setScene(scene);

    // set css
    scene.getStylesheets().add(
      Utilityfuncs.class.getResource(css).toExternalForm()
    );

    // set background
    for (var par : scene.getRoot().getChildrenUnmodifiable()) {
      if (par instanceof Parent) {
        par.getStyleClass().add(
          JMetroStyleClass.BACKGROUND
        );
      }
    }

    // set background
    scene.getRoot().getStyleClass().add(
      JMetroStyleClass.BACKGROUND
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
