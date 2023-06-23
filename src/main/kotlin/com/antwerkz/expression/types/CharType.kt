package com.antwerkz.expression.types

internal class CharType(value: String) : Type(value) {
    init {
        fusible = true
    }
    override fun copy() = CharType(value as String).copy(this)

    override fun evaluate() = value as String
}
