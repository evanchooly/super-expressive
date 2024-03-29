package com.antwerkz.expression.types

internal class StringType(value: String) : Type(value) {
    init {
        quantifierRequiresGroup = true
    }

    override fun copy() = StringType(value as String).copy(this)

    override fun evaluate() = value as String
}
