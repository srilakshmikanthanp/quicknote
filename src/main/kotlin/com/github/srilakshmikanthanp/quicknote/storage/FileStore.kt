package com.github.srilakshmikanthanp.quicknote.storage

import com.github.srilakshmikanthanp.quicknote.interfaces.TextStore
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FileStore : TextStore {
    // File path to store the user Data
    private val filePath: Path = Paths.get(System.getProperty("user.home"), ".quicknote")

    /**
     * Set the Text to the File
     * @param text
     */
    override fun setText(text: String) {
        Files.write(this.filePath, text.toByteArray())
    }

    /**
     * Get the text from the File
     */
    override fun getText(): String = try {
        Files.readString(filePath)
    } catch (e: IOException) {
        ""
    }
}
