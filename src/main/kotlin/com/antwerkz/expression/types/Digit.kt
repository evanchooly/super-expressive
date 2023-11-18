package com.antwerkz.expression.types

internal object Digit : Type("digit") {
    init {
        fusible = true
    }

    override fun copy() = Digit

    override fun evaluate() = "\\d"
}
