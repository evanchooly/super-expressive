package com.antwerkz.expression.types

class StringType(value: String) : Type("string", value) {
    init {
        quantifierRequiresGroup = true
    }
    override fun copy() = StringType(value as String).copy(this)

    override fun evaluate() = value as String
}
