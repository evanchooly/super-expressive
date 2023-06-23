package com.antwerkz.expression.types

internal class AnyOfChars(chars: String) : Type(chars) {
    init {
        fusible = true
    }
    override fun copy() = AnyOfChars(value as String).copy(this)

    override fun evaluate() = "[${value}]"
}
