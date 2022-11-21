package com.antwerkz.expression.types

class AnyOfChars(chars: String) : Type("anyOfChars", chars) {
    override fun copy() = AnyOfChars(value as String).copy(this)

    override fun evaluate() = "[${value}]"
}