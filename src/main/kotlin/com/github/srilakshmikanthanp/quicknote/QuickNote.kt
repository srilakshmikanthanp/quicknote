package com.github.srilakshmikanthanp.quicknote

import javafx.application.Application
import javafx.stage.Stage

/**
 * QuickNote Application
 */
class QuickNote : Application() {
    override fun start(primaryStage: Stage?) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        super.stop()
    }
}

/**
 * Main Method to startup
 */
fun main(args: Array<String>) {
    Application.launch(QuickNote::class.java, *args)
}
