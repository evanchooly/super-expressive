package com.antwerkz.expression.types

object EndOfInput : Type("endOfInput") {
    override fun copy() = EndOfInput

    override fun evaluate() = "$"
}
