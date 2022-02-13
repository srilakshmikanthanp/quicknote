package com.github.srilakshmikanthanp.quicknote

import javafx.application.Application
import javafx.stage.Stage

/**
 * QuickNote Application
 */
class QuickNote : Application() {
    /**
     * Called on Application start by JavaFx
     */
    override fun start(primaryStage: Stage) {

    }

    /**
     * Called on Application stop by JavaFx
     */
    override fun stop() {

    }
}

/**
 * Main Method to startup
 */
fun main(args: Array<String>) {
    Application.launch(QuickNote::class.java, *args)
}
