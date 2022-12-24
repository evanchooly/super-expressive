package com.antwerkz.expression.types

internal object EndOfInput : Type("endOfInput") {
    override fun copy() = EndOfInput

    override fun evaluate() = "$"
}
