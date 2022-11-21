package com.antwerkz.expression.types

object NonWord : Type("nonWord") {
    override fun copy() = NonWord

    override fun evaluate() = "\\W"
}
