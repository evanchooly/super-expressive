package com.antwerkz.expression.types

internal object NonWord : Type("nonWord") {
    init {
        fusible = true
    }

    override fun copy() = NonWord

    override fun evaluate() = "\\W"
}
