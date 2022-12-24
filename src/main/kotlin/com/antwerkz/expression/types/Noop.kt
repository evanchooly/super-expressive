package com.antwerkz.expression.types

internal object Noop : Type("noop") {
    override fun copy() = Noop

    override fun evaluate() = ""
}
