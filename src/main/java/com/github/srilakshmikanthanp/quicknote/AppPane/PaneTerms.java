// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote.AppPane;

import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Terms and Contitions
 */
public class PaneTerms extends BorderPane {
   /**
     * Navitation Title
     */
    final static String navTitle = "License";

    /**
     * get the graphic image
     * 
     * @return ImageView
     */
    private ImageView getLogoImageView() {
        var image = new ImageView(
            new Image(getClass().getResourceAsStream("/images/license.png"))
        );

        image.setFitHeight(100);
        image.setFitWidth(100);

        return image;
    }

    /**
     * Lisence String
     * 
     * @return String
     */
    private String getLicenseString() {
        return "MIT License " +
        "\n\n" +
        "Copyright (c) 2021 Sri Lakshmi Kanthan P " +
        "\n\n" +
        "Permission is hereby granted, free of charge, to any person obtaining a copy " +
        "of this software and associated documentation files (the \"Software\"), to deal " +
        "in the Software without restriction, including without limitation the rights " +
        "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell " +
        "copies of the Software, and to permit persons to whom the Software is " +
        "furnished to do so, subject to the following conditions:" +
        "\n\n" +
        "The above copyright notice and this permission notice shall be included in all " +
        "copies or substantial portions of the Software. " +
        "\n\n"+
        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR " +
        "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, " +
        "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE " +
        "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER " +
        "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, " +
        "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE " +
        "SOFTWARE.";
    }

    /**
     * Constructor
     */
    public PaneTerms() {
        // create the lable content
        Label licenseLabel = new Label(this.getLicenseString());

        licenseLabel.setWrapText(true);
        licenseLabel.setMaxWidth(500);
        licenseLabel.setTextAlignment(TextAlignment.LEFT);
        licenseLabel.setGraphic(this.getLogoImageView());
        licenseLabel.setContentDisplay(ContentDisplay.TOP);

        // add to pane
        this.setPadding(new Insets(20));
        this.setCenter(licenseLabel);
    }
}
