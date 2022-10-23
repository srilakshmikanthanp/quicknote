package com.github.srilakshmikanthanp.quicknote.interfaces

interface TextStore {
    fun setText(text: String)
    fun getText(): String
}
