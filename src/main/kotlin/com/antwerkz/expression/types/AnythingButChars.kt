package com.antwerkz.expression.types

internal class AnythingButChars(chars: String) : Type(chars) {
    override fun copy() = AnythingButChars(value as String).copy(this)

    override fun evaluate() = "[^${value}]"
}
