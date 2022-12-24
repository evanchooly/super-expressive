package com.antwerkz.expression.types

internal class OptionalType : Qualified() {
    override fun copy() = OptionalType().copy(this)

    override fun evaluate(): String {
        return evaluate("?")
    }
}
