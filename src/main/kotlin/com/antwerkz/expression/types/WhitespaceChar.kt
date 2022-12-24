package com.antwerkz.expression.types

internal object WhitespaceChar : Type("whitespaceChar") {
    override fun copy() = WhitespaceChar

    override fun evaluate() = "\\s"
}
