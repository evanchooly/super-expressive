package com.antwerkz.expression.types

object StartOfInput : Type("startOfInput") {
    override fun copy() = StartOfInput

    override fun evaluate() = "^"
}
