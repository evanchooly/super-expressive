package com.antwerkz.expression.types

object NonWhiteSpaceChar : Type("nonWhitespaceChar") {
    override fun copy() = NonWhiteSpaceChar

    override fun evaluate() = "\\S"
}
