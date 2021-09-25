// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.quicknote;

import com.github.srilakshmikanthanp.quicknote.Application.QuickNote;

import javafx.application.*;

/**
 * MainClass for the application.
 */
public class Launch {
    public static void main(String[] args) {
        Application.launch(QuickNote.class, args);
    }
}
