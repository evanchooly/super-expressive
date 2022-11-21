package com.antwerkz.expression.types

class CharType(value: String) : Type("char", value) {
    override fun copy() = CharType(value as String).copy(this)

    override fun evaluate() = value as String
}
