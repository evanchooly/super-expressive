package com.antwerkz.expression.types

object WhitespaceChar : Type("whitespaceChar") {
    override fun copy() = WhitespaceChar

    override fun evaluate() = "\\s"
}
