package com.antwerkz.expression.types

object NullByte : Type("nullByte") {
    override fun copy() = NullByte

    override fun evaluate() = "\\0"
}
