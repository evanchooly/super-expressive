package com.antwerkz.expression.types

internal object NullByte : Type("nullByte") {
    override fun copy() = NullByte

    override fun evaluate() = "\\0"
}
