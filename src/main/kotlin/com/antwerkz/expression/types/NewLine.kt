package com.antwerkz.expression.types

internal object NewLine : Type("newline") {
    override fun copy() = NewLine

    override fun evaluate() = "\\n"
}
