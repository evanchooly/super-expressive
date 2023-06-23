package com.antwerkz.expression.types

internal object NonWhiteSpaceChar : Type("nonWhitespaceChar") {
    init {
        fusible = true
    }
    override fun copy() = NonWhiteSpaceChar

    override fun evaluate() = "\\S"
}
