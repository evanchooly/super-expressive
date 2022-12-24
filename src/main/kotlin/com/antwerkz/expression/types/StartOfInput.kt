package com.antwerkz.expression.types

internal object StartOfInput : Type("startOfInput") {
    override fun copy() = StartOfInput

    override fun evaluate() = "^"
}
