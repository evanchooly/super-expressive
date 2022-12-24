package com.antwerkz.expression.types

internal object Digit : Type("digit") {
    override fun copy() = Digit

    override fun evaluate() = "\\d"
}
