package com.antwerkz.expression.types

internal object WhitespaceChar : Type("whitespaceChar") {
    init {
        fusible = true
    }
    override fun copy() = WhitespaceChar

    override fun evaluate() = "\\s"
}
