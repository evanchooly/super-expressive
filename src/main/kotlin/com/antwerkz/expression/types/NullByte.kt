package com.antwerkz.expression.types

internal object NullByte : Type("nullByte") {
    init {
        fusible = true
    }
    override fun copy() = NullByte

    override fun evaluate() = "\\0"
}
