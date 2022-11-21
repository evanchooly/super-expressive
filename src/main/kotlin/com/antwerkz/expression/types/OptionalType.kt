package com.antwerkz.expression.types

class OptionalType : Qualified() {
    override fun copy() = OptionalType().copy(this)

    override fun evaluate(): String {
        return evaluate("?")
    }
}
