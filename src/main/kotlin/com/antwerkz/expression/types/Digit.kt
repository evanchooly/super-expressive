package com.antwerkz.expression.types

object Digit : Type("digit") {
    override fun copy() = Digit

    override fun evaluate() = "\\d"
}
