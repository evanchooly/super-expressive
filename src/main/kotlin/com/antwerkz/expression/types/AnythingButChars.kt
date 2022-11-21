package com.antwerkz.expression.types

class AnythingButChars(chars: String) : Type("anythingButChars", chars) {
    override fun copy() = AnythingButChars(value as String).copy(this)

    override fun evaluate() = "[^${value}]"
}
