package com.antwerkz.expression.types

object NonWordBoundary : Type("nonWordBoundary") {
    override fun copy() = NonWord

    override fun evaluate() = "\\B"
}
