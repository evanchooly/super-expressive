package com.antwerkz.expression.types

internal object NonWordBoundary : Type("nonWordBoundary") {
    override fun copy() = NonWord

    override fun evaluate() = "\\B"
}
