package com.antwerkz.expression.types

object Noop : Type("noop") {
    override fun copy() = Noop

    override fun evaluate() = ""
}
