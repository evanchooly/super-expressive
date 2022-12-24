package com.antwerkz.expression.types

internal object NonWhiteSpaceChar : Type("nonWhitespaceChar") {
    override fun copy() = NonWhiteSpaceChar

    override fun evaluate() = "\\S"
}
