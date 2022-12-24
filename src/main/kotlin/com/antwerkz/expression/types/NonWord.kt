package com.antwerkz.expression.types

internal object NonWord : Type("nonWord") {
    override fun copy() = NonWord

    override fun evaluate() = "\\W"
}
